package com.washmaintance.service;

import com.washmaintance.pojo.WashRoomPics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liang
* @description 针对表【wash_room_pics】的数据库操作Service
* @createDate 2024-03-24 13:04:19
*/
public interface WashRoomPicsService extends IService<WashRoomPics> {

    void delRoomBannerPic(String id);

    List<WashRoomPics> getWashRoomPicsByID(String id);

    void insertWashRoomBanner(WashRoomPics washRoomPics);
}
