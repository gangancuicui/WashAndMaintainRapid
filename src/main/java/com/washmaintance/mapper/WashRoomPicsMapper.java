package com.washmaintance.mapper;

import com.washmaintance.pojo.WashRoomPics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liang
* @description 针对表【wash_room_pics】的数据库操作Mapper
* @createDate 2024-03-24 13:04:19
* @Entity com.washmaintance.washmaintance.pojo.WashRoomPics
*/
@Mapper
public interface WashRoomPicsMapper extends BaseMapper<WashRoomPics> {
    @Select("select pic from wash_room_pics where wash_room_id=#{id}")
    List<WashRoomPics> getPics(Integer id);

    @Insert("insert into wash_room_pics(wash_room_id,pic,create_time) values(#{washRoomId},#{pic},#{createTime})")
    int addWashRoomPics(WashRoomPics pics);

    @Delete("delete from wash_room_pics where id=#{id}")
    int delWashRoomBannerPic(String id);
    @Select("select * from wash_room_pics where wash_room_id=#{id}")
    List<WashRoomPics> getWashRoomPicsByID(int i);


}




