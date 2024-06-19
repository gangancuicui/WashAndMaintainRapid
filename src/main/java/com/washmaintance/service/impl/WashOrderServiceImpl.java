package com.washmaintance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.washmaintance.mapper.WashDateMapper;
import com.washmaintance.mapper.WashRoomMapper;
import com.washmaintance.pojo.*;
import com.washmaintance.service.WashOrderService;
import com.washmaintance.mapper.WashOrderMapper;
import com.washmaintance.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
* @author liang
* @description 针对表【wash_order】的数据库操作Service实现
* @createDate 2024-03-24 13:04:13
*/
@Service
public class WashOrderServiceImpl extends ServiceImpl<WashOrderMapper, WashOrder>
    implements WashOrderService {
    @Autowired
    private WashOrderMapper washOrderMapper;
    @Autowired
    private WashRoomMapper washRoomMapper;
    @Autowired
    private WashDateMapper washDateMapper;

    @Override
    public void updateWashOrder(WashOrder order) {
        if(order.getId()==null){
            throw new RuntimeException("修改失败");
        }
        if(order.getWashRoomName()!=null){
           order.setWashRoom(washRoomMapper.getIDByName(order.getWashRoomName()).getId());
        }
        int num=washOrderMapper.updateWashOrder(order);
        if (num==0){
            throw new RuntimeException("修改失败");
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public String createOrder(WashOrder washOrder) {
        if(washOrder.getWashTel()==null || washOrder.getWashName()==null){
            throw new RuntimeException("预约信息异常");
        }
            washOrder.setWashStatus(1);
            WashRoom washRoom = washRoomMapper.getIDByName(washOrder.getWashRoomName());
            washOrder.setWashRoom(washRoom.getId());
            WashDate washDate = washDateMapper.getSchedule(washOrder.getWashDate(), washOrder.getWStart(), washOrder.getWEnd(), washOrder.getWashRoom());
            WashOrder washOrder1=washOrderMapper.getOrder(washOrder);
            if(washOrder1!=null){
              throw new RuntimeException("你已预约该时间段");
            }
            if (washDate == null) {
                throw new RuntimeException("异常数据");
            }
            if (washDate.getNum() > 1) {
                String washid = UUID.randomUUID().toString().replace("-", "");
                washOrder.setWashId(washid);
                washOrder.setCreateTime(LocalDateTime.now());
                washOrder.setWashStatus(1);
                int num=washDate.getNum();
                num--;
                washDate.setNum( num);
                washDateMapper.updateWashDateNum(washDate);
                washOrderMapper.insertOrder(washOrder);
                return washid;
            }
            else throw new RuntimeException("该时间段已约满");


    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void cancelOrder(WashOrder washOrder) {
        washOrder.setWashStatus(1);
        WashOrder washOrder1=washOrderMapper.getOrder(washOrder);
        if(washOrder1==null){
            throw new RuntimeException("订单不存在");
        }else {
            washOrderMapper.cancelOrder(washOrder);
            WashDate washDate =new WashDate();
            washDate.setWStart(washOrder1.getWStart());
            washDate.setWDate(washOrder1.getWashDate());
            washDate.setWEnd(washOrder1.getWEnd());
            washDate=washDateMapper.getSchedule(washOrder1.getWashDate(), washOrder1.getWStart(), washOrder1.getWEnd(), washOrder1.getWashRoom());
            washDate.setNum(washDate.getNum()+1);
            washDateMapper.updateWashDateNum(washDate);
        }
    }

    @Override
    public WashOrderDetailBean getOrderDetail(WashOrder washOrder) {
        WashOrderDetailBean washOrderDetailBean = washOrderMapper.getOrderDetail(washOrder);
        if (washOrderDetailBean == null) {
            throw new RuntimeException("订单不存在");
        }
        washOrderDetailBean.setWashRoomName(washRoomMapper.getWashRoomNameByID(washOrderDetailBean.getWashRoom()));
        washOrderDetailBean.setPic(washRoomMapper.getWashRoomPic(washOrderDetailBean.getWashRoom()));
        return washOrderDetailBean;
    }

    @Override
    public void checkOrder(WashOrder washOrder) {
        washOrder.setWashStatus(1);
        WashOrder washOrder1=washOrderMapper.getOrder(washOrder);
        if ( washOrder1 == null) {
            throw new RuntimeException("订单不存在");
        }
    }
    @Transactional
    @Override
    public void verificationOrder(WashOrder washOrder)  {
        washOrder.setWashStatus(1);
        WashOrder washOrder1=washOrderMapper.getOrder(washOrder);
        if ( washOrder1 == null) {
            throw new RuntimeException("订单不存在");
        }
        if(washOrder1.getWashDate().isEqual(LocalDate.now())){
            washOrderMapper.updateOrderByID(washOrder1);
        }else throw new RuntimeException("未到核销时间");

        try {
            WebSocketServer.sendMessage("订单核销成功"+washOrder.getWashId(),washOrder.getWashId());
        }catch (Exception e){
            throw new RuntimeException("网络异常，请稍后再试");
        }
    }

    @Override
    public PageBean getWashOrderList(int page, int size, WashOrder washOrder) {
        if(washOrder.getOpenid()==null){
            throw new RuntimeException("异常数据");
        }
//        try {
            PageHelper.startPage(page,size);
            List<WashOrderBean>list=washOrderMapper.getWashOrderList(washOrder);
            PageInfo<WashOrderBean> p = new PageInfo<WashOrderBean>(list);
            PageBean pageBean = new PageBean((int) p.getTotal(), list);
            return pageBean;
//        }catch (Exception e){
//            throw new RuntimeException(e.toString());
//        }

    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void cancelOverdueOrder() {
        List<WashOrder>washOrders=washOrderMapper.getAllWashOrder();
        if(washOrders.size()==0){
            return;
        }
        List<Integer> ids=new ArrayList<>();
        for(WashOrder washOrder:washOrders){
            if(washOrder.getWashDate().isBefore(LocalDate.now()) ){
                ids.add(washOrder.getId());
            }
        }
        if(ids.size()==0){
            return;
        }
        Integer num=washOrderMapper.cancelOverdueOrder(ids);
        if(num!=ids.size()){
            throw new RuntimeException("取消失败");
        }
    }

    @Transactional
    @Override
    public void delOrderByIds(List<Integer> list) {
        int num=washOrderMapper.delOrderByIds(list);
        if(num!=list.size()){
            throw new RuntimeException("删除失败");
        }
    }
}




