package com.washmaintance.mapper;

import com.washmaintance.pojo.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
* @author liang
* @description 针对表【wash_order】的数据库操作Mapper
* @createDate 2024-03-24 13:04:13
* @Entity com.washmaintance.washmaintance.pojo.WashOrder
*/
@Mapper
public interface WashOrderMapper extends BaseMapper<WashOrder> {


    List<ReserveNum> getReserveNum(List<WashRoomScheduleBean> scheduleList);

    @Select("select count(id) as num from wash_order where wash_date=#{washDate} and w_start=#{wStart} and w_end=#{wEnd} and wash_room=#{washRoom}")
    ScheduleBean getNum(LocalDate washDate, LocalTime wStart, LocalTime wEnd, Integer washRoom);

    @Insert("insert into wash_order (openid, wash_date, wash_room, create_time, wash_id, wash_status, wash_tel, wash_name, w_start, w_end,wash_room_name)VALUES (#{openid},#{washDate},#{washRoom},#{createTime},#{washId},#{washStatus},#{washTel},#{washName},#{wStart},#{wEnd},#{washRoomName}) ")
    void insertOrder(WashOrder washOrder);


    WashOrder getOrder(WashOrder washId);

    @Update("update wash_order set wash_status=3 where wash_id=#{washId} and openid=#{openid}")
    void cancelOrder(WashOrder washOrder);
    @Select("select openid, wash_date, wash_room, create_time, update_time, wash_id, wash_status, wash_tel, wash_name, w_start, w_end from wash_order where wash_id=#{washId} and openid=#{openid}")
    WashOrderDetailBean getOrderDetail(WashOrder washOrder);

    @Update("update wash_order set wash_status=2 where wash_id=#{washId}")
    void updateOrderByID(WashOrder washOrder1);


    List<WashOrderBean> getWashOrderList(WashOrder washOrder);
    @Select("select * from wash_order where wash_status=1")
    List<WashOrder> getAllWashOrder();

    Integer cancelOverdueOrder(List<Integer> ids);

    List<WashOrder> getAllOrder(LocalDate washDateStart,
                                LocalDate washDateEnd,
                                String washRoomName,
                                LocalTime washDateTimeStart,
                                LocalTime washDateTimeEnd,
                                Integer washStatus,
                                String washName,
                                String washId,
                                Integer page,
                                Integer pageSize);

    int updateWashOrder(WashOrder order);

    int delOrderByIds(List<Integer> list);

    @Update("update wash_order set wash_status=#{washStatus} where w_start=#{wStart} and w_end=#{wEnd} and wash_date=#{washDate}")
    void updateWashOrderByDateTime(WashOrder washOrder);
}




