package com.washmaintance.service;

import com.washmaintance.pojo.PageBean;
import com.washmaintance.pojo.WashOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.washmaintance.pojo.WashOrderDetailBean;

import java.util.List;

/**
* @author liang
* @description 针对表【wash_order】的数据库操作Service
* @createDate 2024-03-24 13:04:13
*/
public interface WashOrderService extends IService<WashOrder> {

    void updateWashOrder(WashOrder order);

    String createOrder(WashOrder washOrder);

    void cancelOrder(WashOrder washOrder);

    WashOrderDetailBean getOrderDetail(WashOrder washOrder);

    void checkOrder(WashOrder washOrder);

    void verificationOrder(WashOrder washOrder);

    PageBean getWashOrderList(int page, int size, WashOrder washOrder);

    void cancelOverdueOrder();

    void delOrderByIds(List<Integer> list);
}
