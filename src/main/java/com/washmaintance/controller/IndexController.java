package com.washmaintance.controller;

import com.washmaintance.pojo.Banner;
import com.washmaintance.pojo.Result;
import com.washmaintance.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {
    @Autowired
    private BannerService bannerService;

    @GetMapping("/getIndexBanner")
    Result getBanner(){
        List<Banner>list=bannerService.getIndexBanner();
        return Result.success(list);
    }
}
