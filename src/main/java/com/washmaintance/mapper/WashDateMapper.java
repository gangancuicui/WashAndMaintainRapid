package com.washmaintance.mapper;

import com.washmaintance.pojo.WashDate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.washmaintance.pojo.WashOrder;
import com.washmaintance.pojo.WashRoomScheduleBean;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
* @author liang
* @description 针对表【wash_date】的数据库操作Mapper
* @createDate 2024-03-24 13:04:10
* @Entity com.washmaintance.washmaintance.pojo.WashDate
*/
@Mapper
public interface
WashDateMapper extends BaseMapper<WashDate> {
    @Select("select DISTINCT w_date as date from wash_date where   w_date > #{wDate} and w_room=#{id} ORDER BY w_date ASC ")
    List<WashRoomScheduleBean> getTodayAfterList(WashDate washDate);
    @Select("select w_date,num,w_start,w_end,w_room from wash_date where w_room=#{id} and w_date=#{wDate} and w_start>#{wStart} ORDER BY w_date ASC ,w_start ASC")
    List<WashDate> getTodaySchedule(WashDate washDate);
    @Select("select w_date ,w_start,w_end,num,w_room from wash_date where w_room=#{id} and w_date>#{wDate} ORDER BY w_date ASC ,w_start ASC")
    List<WashDate> getTodayAfterScheduleList(WashDate washDate);

    @Select("select DISTINCT w_date as date from wash_date where w_room=#{id} and w_date=#{wDate} and w_start>#{wStart} ORDER BY w_date ASC")
    List<WashRoomScheduleBean> getScheduleToday(WashDate washDate);

    @Select("select w_date ,w_start ,w_end ,num,w_room  from wash_date where w_room=#{washRoom} and w_date=#{washDate} and w_start=#{wStart} and w_end=#{wEnd}")
    WashDate getSchedule(LocalDate washDate, LocalTime wStart, LocalTime wEnd, Integer washRoom);
    @Update("update wash_date set num=#{num} where w_date=#{wDate} and w_room=#{wRoom} and w_start=#{wStart} and w_end=#{wEnd}")
    void updateWashDateNum(WashDate washDate);

    List<WashDate> getWashDateList(String washRoomName, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Integer wRoom);

    @Update("update wash_date set num=#{num} where id=#{id}")
    int updateWashDateByID(WashDate washDate);
    @Delete("delete from wash_date where id=#{id}")
    int delWashDateByID(int i);
    @Select("select w_date as wash_date, w_start ,w_end from wash_date where id=#{id}")
    WashOrder selectDateById(int i);

    @Insert("insert into wash_date(w_date,w_start,w_end,num,w_room,create_time,wash_room_name) values(#{wDate},#{wStart},#{wEnd},#{num},#{wRoom},#{createTime},#{washRoomName})")
    int insertWashDate(WashDate washDate);

    @Select("select * from wash_date where w_date=#{wDate} and w_start=#{wStart} and w_end=#{wEnd} and w_room=#{wRoom}")
    WashDate getWashDateByDateTimeRoomID(WashDate washDate);
}




