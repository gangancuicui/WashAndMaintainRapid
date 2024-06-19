package com.washmaintance.service;

import com.washmaintance.pojo.Banner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.washmaintance.pojo.PageBean;

import java.util.List;

/**
* @author liang
* @description 针对表【banner】的数据库操作Service
* @createDate 2024-05-29 21:52:50
*/
public interface BannerService extends IService<Banner> {

    PageBean getBanner(Integer page, Integer pageSize, Integer status, String note);

    List<Banner> getIndexBanner();

    void insertBanner(Banner banner);

    void updateBanner(Banner banner);
}
