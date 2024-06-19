package com.washmaintance.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.washmaintance.mapper.WxUserMapper;
import com.washmaintance.pojo.Result;
import com.washmaintance.service.WxUserService;
import com.washmaintance.pojo.WxUser;
import com.washmaintance.utils.JwtUtils;
import com.washmaintance.utils.WechatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
* @author liang
* @description 针对表【wx_user】的数据库操作Service实现
* @createDate 2024-03-24 13:04:21
*/
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser>
    implements WxUserService {
    @Value("${wechat.appId}")
    String appId ;
    @Value("${wechat.appSecret}")
    String appSecret;
    @Value("${wechat.loginUrl}")
    String  loginUrl;
    @Autowired
    WxUserMapper wxUserMapper;
    @Override
    public Result getLoginUser(WxUser wxUser) {
        JSONObject sessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(wxUser.getCode());
        String openid = sessionKeyOpenId.getString("openid");
        String sessionKey = sessionKeyOpenId.getString("session_key");
        if(openid==null||openid.equals("")){
            return Result.error("获取openid失败");
        }
//        String openid="1";
        wxUser.setOpenid(openid);
        WxUser wxUser1=wxUserMapper.getWxuserByOpenid(openid);
        if(wxUser1==null){
            wxUser.setCreateTime(LocalDateTime.now());
            wxUserMapper.insertOpenid(wxUser);
            Map<String, Object> map=new HashMap<>();
            map.put("openid",wxUser.getOpenid());
            WxUser wxUser2=new WxUser();
            wxUser2.setWxToken(JwtUtils.generateJwt(map, 3650L * 24 * 60 * 60 * 1000));
            return Result.success( wxUser2,"注册成功");
        }
        else {
            Map<String, Object> map=new HashMap<>();
            map.put("openid",wxUser1.getOpenid());
            wxUser1.setWxToken(JwtUtils.generateJwt(map));
            return Result.success( wxUser1,"登陆成功");
        }
    }

    @Override
    public void updateUserInfo(WxUser wxUser) {
        if(wxUser.getOpenid()==null){
            throw new RuntimeException("未登录");
        }
        wxUserMapper.updateUserInfo(wxUser);
    }
}




