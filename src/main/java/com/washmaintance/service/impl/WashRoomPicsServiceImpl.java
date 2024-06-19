package com.washmaintance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.washmaintance.service.WashRoomPicsService;
import com.washmaintance.pojo.WashRoomPics;
import com.washmaintance.mapper.WashRoomPicsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author liang
* @description 针对表【wash_room_pics】的数据库操作Service实现
* @createDate 2024-03-24 13:04:19
*/
@Service
public class WashRoomPicsServiceImpl extends ServiceImpl<WashRoomPicsMapper, WashRoomPics>
    implements WashRoomPicsService {
    @Autowired
    private WashRoomPicsMapper washRoomPicsMapper;
    @Override
    public void delRoomBannerPic(String id) {
        int num=washRoomPicsMapper.delWashRoomBannerPic(id);
        if(num==0){
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public List<WashRoomPics> getWashRoomPicsByID(String id) {
        List<WashRoomPics> list=washRoomPicsMapper.getWashRoomPicsByID(Integer.parseInt(id));
        return list;
    }

    @Override
    public void insertWashRoomBanner(WashRoomPics washRoomPics) {
        if(washRoomPics.getPic()==null || washRoomPics.getPic().equals("") || washRoomPics.getWashRoomId()==null || washRoomPics.getWashRoomId().equals("")){
            throw new RuntimeException("参数不能为空");
        }
        washRoomPics.setCreateTime(LocalDateTime.now());
        int num=washRoomPicsMapper.addWashRoomPics(washRoomPics);
        if (num==0){
            throw new RuntimeException("添加失败");
        }
    }
}




