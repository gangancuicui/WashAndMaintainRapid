package com.washmaintance.service;

import com.washmaintance.pojo.PageBean;
import com.washmaintance.pojo.WashDate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.time.LocalTime;

/**
* @author liang
* @description 针对表【wash_date】的数据库操作Service
* @createDate 2024-03-24 13:04:10
*/
public interface WashDateService extends IService<WashDate> {

    PageBean getWashDateList(Integer page, Integer pageSize, String washRoomName, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Integer wRoom);

    void updateWashDateByID(WashDate washDate);

    void delWashDateByID(String id);

    void insertWashDate(WashDate washDate);
}
