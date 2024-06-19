package com.washmaintance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.washmaintance.mapper.WashDateMapper;
import com.washmaintance.mapper.WashOrderMapper;
import com.washmaintance.mapper.WashRoomPicsMapper;
import com.washmaintance.pojo.*;
import com.washmaintance.service.WashRoomService;
import com.washmaintance.mapper.WashRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* @author liang
* @description 针对表【wash_room】的数据库操作Service实现
* @createDate 2024-03-24 13:04:16
*/
@Service
public class WashRoomServiceImpl extends ServiceImpl<WashRoomMapper, WashRoom>
    implements WashRoomService{
    @Autowired
    private WashRoomMapper washRoomMapper;
    @Autowired
    private WashRoomPicsMapper washRoomPics;
    @Autowired
    private WashDateMapper washDateMapper;
    @Autowired
    private WashOrderMapper washOrderMapper;
    @Autowired
    private WashRoomPicsMapper washRoomPicsMapper;

    @Override
    public PageBean getWashRoomList(Integer page, Integer size, String name) {
        PageBean pageBean =new PageBean();
        PageHelper.startPage(page,size);

        if(name.equals("洗车")){
            List<WashRoom> list = washRoomMapper.getWashRoomList(1);
            PageInfo<WashRoom> p = new PageInfo<WashRoom>(list);
            pageBean =new PageBean((int) p.getTotal(),list);
        } else if (name.equals("养护")) {
            List<WashRoom> list = washRoomMapper.getWashRoomList(2);
            PageInfo<WashRoom> p = new PageInfo<WashRoom>(list);
            pageBean =new PageBean((int) p.getTotal(),list);
        }
        return pageBean;
    }

    @Override
    public WashRoomDetailBeam getWashRoomDetailByName(String name) {
        WashRoomDetailBeam washRoomDetailBeam =washRoomMapper.getWashRoomByName(name);
        WashRoom temp=washRoomMapper.getIDByName(name);
        washRoomDetailBeam.setPics(washRoomPics.getPics(temp.getId()));

        return washRoomDetailBeam;
    }

    @Override
    public List<WashRoomScheduleBean> getWashRoomSchedule(String name) {
        List<ScheduleBean> scheduleBeanList=new ArrayList<>();
        WashRoom temp=washRoomMapper.getIDByName(name);
        WashDate washDate=new WashDate();
        washDate.setId(temp.getId());
        washDate.setWDate(LocalDate.now());
        washDate.setWStart(LocalTime.now());
        List<WashRoomScheduleBean> todayScheduleList=washDateMapper.getScheduleToday(washDate);
        List<WashRoomScheduleBean> todayAfterScheduleList=washDateMapper.getTodayAfterList(washDate);
        if(todayScheduleList.size()>0){
            WashRoomScheduleBean temp1=todayScheduleList.get(0);
            temp1.setWSchedule(washDateMapper.getTodaySchedule(washDate));
            todayScheduleList.set(0,temp1);
        }
        List<WashDate> todayAfterSchedule=washDateMapper.getTodayAfterScheduleList(washDate);
        for (WashRoomScheduleBean scheduleBean : todayAfterScheduleList) {
            List<WashDate> temp2 = new ArrayList<>();
            for (WashDate washDate1 : todayAfterSchedule) {
                if (washDate1.getWDate().equals(scheduleBean.getDate())) {
                    temp2.add(washDate1);
                }
            }
            scheduleBean.setWSchedule(temp2);
            if (temp2.isEmpty()) {
                break; // 如果 temp2 为空,则退出外层循环
            }
        }

        List<WashRoomScheduleBean> result= Stream.concat(todayScheduleList.stream(), todayAfterScheduleList.stream())
                .collect(Collectors.toList());
//        List <ReserveNum> reserveNumList =washOrderMapper.getReserveNum(result);
//        for (int i = 0; i < result.size(); i++) {
//            WashRoomScheduleBean temp2=result.get(i);
//            for (int i1 = 0; i1 < temp2.getWSchedule().size(); i1++) {
//                List<WashDate> temp3=temp2.getWSchedule();
//                for (int i2 = 0; i2 < temp3.size(); i2++) {
//                    for (int i3 = 0; i3 < reserveNumList.size(); i3++) {
//                        if(temp3.get(i2).getWDate().equals(reserveNumList.get(i3).getDate()) && temp3.get(i2).getWStart().equals(reserveNumList.get(i3).getStartTime()) && temp3.get(i2).getWEnd().equals(reserveNumList.get(i3).getEndTime()) && temp3.get(i2).getWRoom().equals(reserveNumList.get(i3).getWashRoom()) ){
//                            WashDate tenp4=temp3.get(i2);
//                            tenp4.setNum(temp3.get(i2).getNum()-reserveNumList.get(i3).getCount());
//                            temp3.set(i2,tenp4);
//                        }
//                     }
////                    temp2.setWSchedule(temp3);
//                }
//                temp2.setWSchedule(temp3);
//            }
//            result.set(i,temp2);
//        }
        return result;
    }

    @Transactional
    @Override
    public void addWashRoom(WashRoomDetailBeam washRoom) {
        if(washRoom.getUsages()==null||washRoom.getUsages().equals("")){
            throw new RuntimeException("请输入用途");
        }
        if(washRoom.getPic()==null){
            throw new RuntimeException("请上传图片");
        }
        if(washRoom.getWName()==null||washRoom.getWName().equals("")){
            throw new RuntimeException("请输入名称");
        }
        if(washRoom.getWAddress()==null||washRoom.getWAddress().equals("")){
            throw new RuntimeException("请输入地址");
        }

        washRoom.setCreateTime(LocalDateTime.now());
        int num=washRoomMapper.addWashRoom(washRoom);
        if(num==0){
            throw new RuntimeException("添加失败");
        }
        List<WashRoomPics>list=washRoom.getPics();
        for (int i = 0; i < list.size(); i++) {
            WashRoomPics pics=list.get(i);
            pics.setWashRoomId(washRoom.getId());
            pics.setCreateTime(LocalDateTime.now());
            if(washRoomPicsMapper.addWashRoomPics(pics)==0){
                throw new RuntimeException("添加失败");
            }
        }
    }

    @Override
    public PageBean getWashRoomByName(Integer page, Integer pageSize, String washRoomName) {
        PageBean pageBean =new PageBean();
        PageHelper.startPage(page,pageSize);

        if(washRoomName==null||washRoomName.equals("")){
            List<WashRoom> list = washRoomMapper.getAllWashRoom();
            PageInfo<WashRoom> p = new PageInfo<WashRoom>(list);
            pageBean =new PageBean((int) p.getTotal(),list);
            return pageBean;
        }
        else {
            List<WashRoom> list  =washRoomMapper.getWashRoomListByName(washRoomName);
            PageInfo<WashRoom> p = new PageInfo<WashRoom>(list);
            pageBean =new PageBean((int) p.getTotal(),list);
            return pageBean;
        }


    }

    @Override
    public WashRoomDetailBeam getWashRoomDetailByID(String id) {
        int id1=Integer.parseInt(id);
        WashRoomDetailBeam washRoomDetailBeam =washRoomMapper.getWashRoomByID(id1);
//        washRoomDetailBeam.setPics(washRoomPics.getPics(id1));
        return washRoomDetailBeam;
    }

    @Override
    public void updateWashRoomByID(WashRoom washRoom) {
        if (washRoom.getId()==null){
            throw new RuntimeException("id不能为空");
        }
        if (washRoom.getUsages()!=null){
            if(washRoom.getUsages() > 3|| washRoom.getUsages()<1){
                throw new RuntimeException("错误类型");
            }
        }
        int num=washRoomMapper.updateWashRoomByID(washRoom);
        if(num==0){
            throw new RuntimeException("修改失败");
        }
    }


}




