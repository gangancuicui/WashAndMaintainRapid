package com.washmaintance.service;

import com.washmaintance.pojo.Result;
import com.washmaintance.pojo.WxUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liang
* @description 针对表【wx_user】的数据库操作Service
* @createDate 2024-03-24 13:04:21
*/
public interface WxUserService extends IService<WxUser> {

    Result getLoginUser(WxUser wxUser);

    void updateUserInfo(WxUser wxUser);
}
