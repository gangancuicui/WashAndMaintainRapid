package com.washmaintance.service;

import com.washmaintance.pojo.WashRoom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.washmaintance.pojo.WashRoomDetailBeam;
import com.washmaintance.pojo.PageBean;
import com.washmaintance.pojo.WashRoomScheduleBean;

import java.util.List;

/**
* @author liang
* @description 针对表【wash_room】的数据库操作Service
* @createDate 2024-03-24 13:04:16
*/
public interface WashRoomService extends IService<WashRoom> {

    PageBean getWashRoomList(Integer page, Integer size, String name);


    WashRoomDetailBeam getWashRoomDetailByName(String name);

    List<WashRoomScheduleBean> getWashRoomSchedule(String name);

    void addWashRoom(WashRoomDetailBeam washRoom);

    PageBean getWashRoomByName(Integer page, Integer pageSize, String washRoomName);

    WashRoomDetailBeam getWashRoomDetailByID(String id);

    void updateWashRoomByID(WashRoom washRoom);


}
