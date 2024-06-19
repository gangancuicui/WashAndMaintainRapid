package com.washmaintance.mapper;

import com.washmaintance.pojo.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liang
* @description 针对表【wash_room】的数据库操作Mapper
* @createDate 2024-03-24 13:04:16
* @Entity com.washmaintance.washmaintance.pojo.WashRoom
*/
@Mapper
public interface
WashRoomMapper extends BaseMapper<WashRoom> {
    @Select("select w_name,w_address,pic from wash_room where usages=#{usage}")
    List<WashRoom> getWashRoomList(int usage);
    @Select("select w_name,w_address,pic from wash_room where w_name=#{name}")
    WashRoomDetailBeam getWashRoomByName(String name);

    @Select("select id from wash_room where w_name=#{name}")
    WashRoom getIDByName(String name);

    @Select("select pic from wash_room where id=#{washRoom}")
    String getWashRoomPic(Integer washRoom);
    @Select("select w_name from wash_room where id=#{washRoom}")
    String getWashRoomNameByID(Integer washRoom);

    int addWashRoom(WashRoomDetailBeam washRoom);

    @Select("select * from wash_room")
    List<WashRoom> getAllWashRoom();


    @Select("select * from wash_room where w_name like concat('%', #{washName}, '%')")
    List<WashRoom> getWashRoomListByName(String washRoomName);

    @Select("select * from wash_room where id=#{id1}")
    WashRoomDetailBeam getWashRoomByID(int id1);

    int updateWashRoomByID(WashRoom washRoom);

    @Select("select * from wash_room where id=#{id} and w_name=#{washRoomName}")
    WashRoom getWashRoomByNameID(Integer id, String washRoomName);
}




