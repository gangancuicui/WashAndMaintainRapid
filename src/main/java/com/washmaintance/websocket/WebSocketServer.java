package com.washmaintance.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@Service
@ServerEndpoint("/getVerificationMsg/{OrderID}")
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String OrderID = "";

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("OrderID") String OrderID) {
        this.session = session;
        this.OrderID = OrderID;
        webSocketMap.put(this.OrderID,this);     // 加入set中

        addOnlineCount();           // 在线数加1
        try {
            sendMessage("conn_success");
            log.info("有订单开启核销,订单号=" + OrderID + ",当前在线人数为:" + getOnlineCount());
        } catch (IOException e) {
            log.error("websocket IO Exception");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(this);  // 从set中删除
        subOnlineCount();              // 在线数减1
        // 断开连接情况下，更新主板占用情况为释放
        log.info("释放的订单号=" + OrderID );
        releaseResource();
    }

    private void releaseResource() {
        // 这里写释放资源和要处理的业务
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
//        log.info("收到来自客户端 sid=" + sid + " 的信息:" + message);
        // 群发消息
//        HashSet<String> sids = new HashSet<>();
//        for (WebSocketServer item : webSocketSet) {
//            sids.add(item.sid);
//        }
//        try {
//            sendMessage("客户端 " + this.sid + "发布消息：" + message, sids);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 发生错误回调
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(session.getBasicRemote() + "客户端发生错误");
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     */
    public static void sendMessage(String message, String orderID) throws IOException {
        webSocketMap.get(orderID).getSession().getBasicRemote().sendText(message);
    }

    /**
     * 实现服务器主动推送消息到 指定客户端
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 获取当前在线人数
     *
     * @return
     */
    public static int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 当前在线人数 +1
     *
     * @return
     */
    public static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    /**
     * 当前在线人数 -1
     *
     * @return
     */
    public static void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

    /**
     * 获取当前在线客户端对应的WebSocket对象
     *
     * @return
     */
    public static ConcurrentHashMap<String,WebSocketServer> getWebSocketMap() {
        return webSocketMap;
    }
}
