package com.washmaintance.mapper;

import com.washmaintance.pojo.WxUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* @author liang
* @description 针对表【wx_user】的数据库操作Mapper
* @createDate 2024-03-24 13:04:21
* @Entity com.washmaintance.washmaintance.pojo.WxUser
*/
@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {
@Select("select  openid, nickname, avatarurl, name, tel from wx_user where openid = #{openid}")
    WxUser getWxuserByOpenid(String openid);

    void insertOpenid(WxUser wxUser);

    void updateUserInfo(WxUser wxUser);
}




