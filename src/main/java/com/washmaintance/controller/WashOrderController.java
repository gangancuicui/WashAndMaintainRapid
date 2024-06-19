package com.washmaintance.controller;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.washmaintance.anno.LoginInterceptor;
import com.washmaintance.pojo.*;
import com.washmaintance.service.WashOrderService;
import com.washmaintance.utils.QRCodeUtils;
import com.washmaintance.websocket.WebSocketServer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
public class WashOrderController {
    @Autowired
    WashOrderService washOrderService;

    @LoginInterceptor
    @PostMapping("/orderWash")
    Result orderWash(@RequestBody WashOrder washOrder, HttpServletRequest req){
        try {
            washOrder.setOpenid((String) req.getAttribute("openid"));
//            washOrder.setOpenid("1");
            String orderId=washOrderService.createOrder(washOrder);
            return Result.success(orderId);
        }catch (Exception e){
            return Result.error(e.getMessage());
        }

    }
    @LoginInterceptor
    @GetMapping("/cancelOrder/{washId}")
    Result cancelOrder(@PathVariable String washId, HttpServletRequest req){
        WashOrder washOrder=new WashOrder();
        washOrder.setWashId(washId);
        washOrder.setOpenid((String) req.getAttribute("openid"));

        try {
            washOrderService.cancelOrder(washOrder);
        }catch (RuntimeException e){
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    @GetMapping("/VerificationOrder/{orderId}")
    public Result sendVerificationMsg(@PathVariable("orderId") String orderId) {
//        Map<String, Object> result = new HashMap<>();
//        try {
//            HashSet<String> sids = new HashSet<>();
//            sids.add(cid);
//            WebSocketServer.sendMessage("服务端推送消息：" + message, sids);\
//            result.put("code", cid);
//            result.put("msg", message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            WashOrder washOrder=new WashOrder();
            washOrder.setWashId(orderId);
            washOrderService.verificationOrder(washOrder);
            return Result.success("订单核销成功"+orderId);
        }catch (Exception e){
            return Result.error(e.getMessage());
        }

    }
    @LoginInterceptor
    @GetMapping("/getOrderDetails/{orderId}")
    public Result getOrderDetails(@PathVariable String orderId, HttpServletRequest req){
        try {
            WashOrder washOrder=new WashOrder();
            washOrder.setWashId(orderId);
//            washOrder.setOpenid("1");
            washOrder.setOpenid((String) req.getAttribute("openid"));
            WashOrderDetailBean washOrderDetailBean=washOrderService.getOrderDetail(washOrder);
            return Result.success(washOrderDetailBean);
        }catch (Exception e){
            return Result.error(e.getMessage());
        }

    }
    @LoginInterceptor
    @GetMapping("/getVerificationQRCode/{orderID}")
    public void getVerificationQRCode(@PathVariable String orderID, HttpServletResponse response, HttpServletRequest req) throws Exception {
        try {
            response.setContentType("image/jpg");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            OutputStream stream = response.getOutputStream();

            //type是1，生成活动详情、报名的二维码，type是2，生成活动签到的二维码
            String content = orderID;
            WashOrder washOrder = new WashOrder();
            washOrder.setWashId(content);
            washOrder.setOpenid((String) req.getAttribute("openid"));

            washOrderService.checkOrder(washOrder);
            //获取一个二维码图片
            BitMatrix bitMatrix = QRCodeUtils.createCode(content);
            //以流的形式输出到前端
            MatrixToImageWriter.writeToStream(bitMatrix, "jpg", stream);
        }
        catch (Exception e){
            throw e;
        }
    }
    @LoginInterceptor
    @GetMapping("/getWashOrderList")
    public Result getWashOrderList(HttpServletRequest req,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "5") Integer size,
                                   @RequestParam(required = true) Integer status){
        try {
            WashOrder washOrder=new WashOrder();
            washOrder.setOpenid((String) req.getAttribute("openid"));
            washOrder.setWashStatus(status);
//            washOrder.setOpenid("1");
            PageBean list=washOrderService.getWashOrderList(page,size,washOrder);
            return Result.success(list);
        }
        catch (Exception e){
            return Result.error(e.getMessage());
        }

    }

}
