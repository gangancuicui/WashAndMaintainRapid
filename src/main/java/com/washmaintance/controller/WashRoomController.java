package com.washmaintance.controller;

import com.washmaintance.pojo.Result;
import com.washmaintance.pojo.WashRoomDetailBeam;
import com.washmaintance.pojo.PageBean;
import com.washmaintance.pojo.WashRoomScheduleBean;
import com.washmaintance.service.WashRoomService;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WashRoomController {
    @Autowired
    private WashRoomService washRoomService;
    @GetMapping("/washRoomList")
    Result getWashRoomList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size,@RequestParam String name){
        PageBean pageBean =washRoomService.getWashRoomList(page,size,name);
        return Result.success(pageBean);
    }
    @GetMapping("/washRoomDetail/{name}")
    Result getWashRoomDetailByName(@PathVariable String name){
        WashRoomDetailBeam washRoomDetailBeam=washRoomService.getWashRoomDetailByName(name);
        return Result.success(washRoomDetailBeam);
    }
    @GetMapping("/washRoomSchedule")
    Result getWashRoomSchedule(@RequestParam String name){
        try{List<WashRoomScheduleBean> washRoomScheduleBean=washRoomService.getWashRoomSchedule(name);
        return Result.success(washRoomScheduleBean);}
        catch (Exception e){
            return Result.error("数据异常");
        }
    }

}
