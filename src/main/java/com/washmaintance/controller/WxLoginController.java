package com.washmaintance.controller;

import com.washmaintance.anno.LoginInterceptor;
import com.washmaintance.pojo.Result;
import com.washmaintance.pojo.WxUser;
import com.washmaintance.service.WxUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class WxLoginController {
    @Autowired
    private WxUserService wxUserService;
    @PostMapping("/login")
    Result login(@RequestBody WxUser wxUser){
        try{
            Result result= wxUserService.getLoginUser(wxUser);
            return result;
        }
        catch (Exception e){
            return Result.error("数据异常");
        }
    }
    @LoginInterceptor
    @PostMapping("/updateUserInfo")
    Result updateUserInfo(@RequestBody WxUser wxUser, HttpServletRequest req){
        wxUser.setOpenid((String) req.getAttribute("openid"));
        try{
            wxUserService.updateUserInfo(wxUser);}
        catch (Exception e){
            return Result.error(e.getMessage());
        }
        return Result.success("更新成功");
    }
    @LoginInterceptor
    @RequestMapping("/uploadAvatar")
    Result uploadAvatar(@RequestParam("image") MultipartFile image) {
        System.out.println("上传文件");
        try{
            String oFileName=image.getOriginalFilename();
            String newFileName= UUID.randomUUID().toString()+oFileName.substring(oFileName.lastIndexOf("."));
            String filePath="D:/projectFIle/washmaintance/avatar/"+newFileName;
            image.transferTo(new File(filePath));
            String url="http://192.168.2.140:7070/avatar/"+newFileName;
            return  Result.success(url);
        }
        catch (Exception e)
            {
                return Result.error(e.getMessage());
            }
    }
}
