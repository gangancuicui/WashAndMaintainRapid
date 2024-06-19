package com.washmaintance.controller;

import com.github.pagehelper.Page;
import com.washmaintance.anno.ManagerInterceptor;
import com.washmaintance.mapper.WashOrderMapper;
import com.washmaintance.pojo.*;
import com.washmaintance.service.*;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class MannagerController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private WashOrderService washOrderService;
    @Autowired
    private WashRoomService washRoomService;
    @Autowired
    private WashRoomPicsService washRoomPicsService;
    @Autowired
    private WashDateService washDateService;
    @Autowired
    private BannerService bannerService;

    @PostMapping("/managerLogin")
    Result mangerLogin(@RequestBody Manager manager){
        try {
            String code = managerService.managerLogin(manager);
            return Result.success(code);
        }
        catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
    @ManagerInterceptor
    @GetMapping("/getAllOrder")
    Result getAllOrder(@RequestParam(required = false) LocalDate washDateStart,
                       @RequestParam(required = false) LocalDate washDateEnd,
                       @RequestParam(required = false) String washRoomName,
                       @RequestParam(required = false) LocalTime washDatetimeStart,
                       @RequestParam(required = false) LocalTime washDatetimeEnd,
                       @RequestParam(required = false) Integer washStatus,
                       @RequestParam(required = false) String washName,
                       @RequestParam(required = false) String washId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer pageSize){

        try {

//            LocalDate washDateStart1 = LocalDate.parse(washDateStart, DateTimeFormatter.ISO_DATE);
//            LocalDate washDateEnd1 = LocalDate.parse(washDateEnd, DateTimeFormatter.ISO_DATE);
//            LocalTime washDatetimeStart1 = LocalTime.parse(washDatetimeStart, DateTimeFormatter.ISO_TIME);
//            LocalTime washDatetimeEnd1 = LocalTime.parse(washDatetimeEnd, DateTimeFormatter.ISO_TIME);
            PageBean list =managerService.getAllOrder(washDateStart,
                    washDateEnd,washRoomName,washDatetimeStart,
                    washDatetimeEnd,washStatus,washName,washId,page,pageSize);
            return Result.success(list);

        }
        catch (Exception e){
            return Result.error(e.getMessage());
        }

    }
    @ManagerInterceptor
    @PostMapping("/updateOrder")
    Result updateOrder(@RequestBody WashOrder order){
        try {
            washOrderService.updateWashOrder(order);
            return Result.success();
        }
        catch (Exception e){
            return Result.error(e.getMessage());
        }

    }
    @ManagerInterceptor
    @PostMapping("/delOrder")
    Result delOrder(@RequestBody int[] list){
        List<Integer> list1 = Arrays.stream(list).boxed().collect(Collectors.toList());
        try{
            washOrderService.delOrderByIds(list1);
            return Result.success();
        }
        catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
    @ManagerInterceptor
    @PostMapping("/addWashRoom")
    Result addWashRoom(@RequestBody WashRoomDetailBeam washRoom){
        try {
            washRoomService.addWashRoom(washRoom);
            return Result.success();
        }
        catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
    @ManagerInterceptor
    @GetMapping("/getWashRoom")
    Result getWashRoom(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam (defaultValue = "5") Integer pageSize,
            @RequestParam(required = false) String washRoomName,
            @RequestParam(required = false) Integer usages){
        //List<WashRoom>
        return Result.success();
    }
    @ManagerInterceptor
    @RequestMapping("/uploadRoomPic")
    Result uploadRoomPic (@RequestParam("image") MultipartFile image) throws IOException {
        String oFileName=image.getOriginalFilename();
        String newFileName= UUID.randomUUID().toString()+oFileName.substring(oFileName.lastIndexOf("."));
        String filePath="D:/projectFIle/washmaintance/roomPic/"+newFileName;
        image.transferTo(new File(filePath));
        String url="http://192.168.2.140:7070/roomPic/"+newFileName;
        return  Result.success(url);
    }
    @ManagerInterceptor
    @RequestMapping("/uploadRoomBannerPic")
    Result uploadRoomBannerPic(@RequestParam("image") MultipartFile image) throws IOException {
        String oFileName=image.getOriginalFilename();
        String newFileName= UUID.randomUUID().toString()+oFileName.substring(oFileName.lastIndexOf("."));
        String filePath="D:/projectFIle/washmaintance/roomBannerPic/"+newFileName;
        image.transferTo(new File(filePath));
        String url="http://192.168.2.140:7070/roomBannerPic/"+newFileName;
        return  Result.success(url);
    }
    @ManagerInterceptor
    @GetMapping("/delRoomBannerPic")
    Result delRoomBannerPic(@RequestParam("id") String id){
        try{
            washRoomPicsService.delRoomBannerPic(id);
            return Result.success();}
        catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
    @ManagerInterceptor
    @GetMapping("/getWashRoomByName")
    Result getWashRoomByName(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam (defaultValue = "5") Integer pageSize,
                             @RequestParam(required = false) String washRoomName) {

        try {
            PageBean pageBean = washRoomService.getWashRoomByName(page, pageSize, washRoomName);
            return Result.success(pageBean);
        }
        catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
    @ManagerInterceptor
    @GetMapping("/getWashRoomByid")
    Result getWashRoomDetailByid(@RequestParam("id") String id){
       try{
           WashRoomDetailBeam washroom=washRoomService.getWashRoomDetailByID(id);
           return Result.success(washroom);}
       catch (Exception e){
           return Result.error(e.getMessage());
       }
    }
    @ManagerInterceptor
    @GetMapping("/getWashRoomBannerPicByRoomID")
    Result getWashRoomBannerPicByid(@RequestParam("id") String id){
        List<WashRoomPics> list=washRoomPicsService.getWashRoomPicsByID(id);
        return Result.success(list);
    }

    @ManagerInterceptor
    @PostMapping("/updateWashRoomByID")
    Result updateWashRoomByID(@RequestBody WashRoom washRoom){
       try{
           washRoomService.updateWashRoomByID(washRoom);
        return Result.success();
    }
       catch (Exception e){
           return Result.error(e.getMessage());
       }
    }
    @ManagerInterceptor
    @PostMapping("/insertWashRoomBannerPic")
    Result insertWashRoomBanner(@RequestBody WashRoomPics washRoomPics){
        try {
            washRoomPicsService.insertWashRoomBanner(washRoomPics);
            return Result.success();
        }
        catch (Exception e){
            return Result.error(e.getMessage());
        }

    }
    @ManagerInterceptor
    @GetMapping("/getWashDateList")
    Result getWashDateList(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam (defaultValue = "5") Integer pageSize,
                          @RequestParam(required = false) String washRoomName,
                          @RequestParam(required = false) LocalDate startDate,
                          @RequestParam(required = false) LocalDate endDate,
                          @RequestParam(required = false) LocalTime startTime,
                          @RequestParam(required = false) LocalTime endTime,
                          @RequestParam(required = false) Integer wRoom){
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               PageBean list =washDateService.getWashDateList(page,pageSize,washRoomName,startDate,endDate,startTime,endTime,wRoom);
        return Result.success(list);
    }
    @ManagerInterceptor
    @PostMapping("/updateWashDateByID")
    Result updateWashDateByID(@RequestBody WashDate washDate){
       try{ washDateService.updateWashDateByID(washDate);
        return Result.success();}
       catch (Exception e){
           return Result.error(e.getMessage());
       }
    }
    @ManagerInterceptor
    @GetMapping("/delWashDateByID")
    Result delWashDateByID(@RequestParam("id") String id){
        washDateService.delWashDateByID(id);
        return Result.success();
    }
    @ManagerInterceptor
    @PostMapping("/insertWashDate")
    Result insertWashDate(@RequestBody WashDate washDate){
       try{
           washDateService.insertWashDate(washDate);
            return Result.success();
       }
       catch (Exception e){
           return Result.error(e.getMessage());
       }
    }
    @ManagerInterceptor
    @GetMapping("/getBanner")
    Result getBanner(@RequestParam (defaultValue  = "1") Integer page,
                     @RequestParam (defaultValue  = "5") Integer pageSize,
                     @RequestParam(required = false) Integer status,
                     @RequestParam(required = false) String note){

       PageBean list= bannerService.getBanner(page,pageSize,status,note);

        return Result.success(list);
    }

    @ManagerInterceptor
    @RequestMapping("/uploadIndexBannerPic")
    Result uploadIndexBannerPic(@RequestParam("image") MultipartFile image) throws IOException {
        String oFileName=image.getOriginalFilename();
        String newFileName= UUID.randomUUID().toString()+oFileName.substring(oFileName.lastIndexOf("."));
        String filePath="D:/projectFIle/washmaintance/bannerPic/"+newFileName;
        image.transferTo(new File(filePath));
        String url="http://192.168.2.140:7070/bannerPic/"+newFileName;
        return  Result.success(url);
    }
    @ManagerInterceptor
    @PostMapping("/insertBanner")
    Result insertBanner(@RequestBody Banner banner){
        try {
            bannerService.insertBanner(banner);
            return Result.success();
        }
        catch (Exception e){
            return Result.error(e.getMessage());
        }

    }
    @ManagerInterceptor
    @PostMapping("/updateBanner")
    Result updateBanner(@RequestBody Banner banner){
        try {
            bannerService.updateBanner(banner);
            return Result.success();
        }
        catch (Exception e){
            return Result.error(e.getMessage());
        }
    }



}
