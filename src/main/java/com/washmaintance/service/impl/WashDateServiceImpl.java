package com.washmaintance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.washmaintance.mapper.WashDateMapper;
import com.washmaintance.mapper.WashOrderMapper;
import com.washmaintance.mapper.WashRoomMapper;
import com.washmaintance.pojo.PageBean;
import com.washmaintance.pojo.WashOrder;
import com.washmaintance.pojo.WashRoom;
import com.washmaintance.service.WashDateService;
import com.washmaintance.pojo.WashDate;
import com.washmaintance.service.WashOrderService;
import com.washmaintance.service.WashRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
* @author liang
* @description 针对表【wash_date】的数据库操作Service实现
* @createDate 2024-03-24 13:04:10
*/
@Service
public class WashDateServiceImpl extends ServiceImpl<WashDateMapper, WashDate>
    implements WashDateService {
    @Autowired
    private WashDateMapper washDateMapper;
    @Autowired
    private WashOrderMapper washOrderMapper;
    @Autowired
    private WashRoomMapper washRoomMapper;



    @Override
    public PageBean getWashDateList(Integer page, Integer pageSize, String washRoomName, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Integer wRoom) {
        PageBean pageBean =new PageBean();
        PageHelper.startPage(page,pageSize);
        List<WashDate> list = washDateMapper.getWashDateList(washRoomName,startDate,endDate,startTime,endTime,wRoom);
        PageInfo<WashDate> p = new PageInfo<WashDate>(list);
        pageBean =new PageBean((int) p.getTotal(),list);
        return pageBean;
    }

    @Override
    public void updateWashDateByID(WashDate washDate) {
        if(washDate.getId()==null || washDate.getNum()==null){
            throw new RuntimeException("参数不能为空");
        }
       int num = washDateMapper.updateWashDateByID(washDate);
       if(num==0){
           throw new RuntimeException("修改失败");
       }
    }

    @Transactional
    @Override
    public void delWashDateByID(String id) {
        WashOrder washOrder=washDateMapper.selectDateById(Integer.parseInt(id));
        washOrder.setWashStatus(3);
        int num=washDateMapper.delWashDateByID(Integer.parseInt(id));
        washOrderMapper.updateWashOrderByDateTime(washOrder);
        if (num==0){
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public void insertWashDate(WashDate washDate) {
        if(washDate.getWashRoomName()==null
                || washDate.getWDate()==null
                || washDate.getWStart()==null
                || washDate.getWEnd()==null
                || washDate.getNum()==null || washDate.getWRoom()==null){

            throw new RuntimeException("参数不能为空");
        }
        WashDate washDate1=washDateMapper.getWashDateByDateTimeRoomID(washDate);
        if(washDate1 != null){
            throw new RuntimeException("该时间段已存在记录");
        }
        WashRoom washRoom=washRoomMapper.getWashRoomByNameID(washDate.getWRoom(),washDate.getWashRoomName());
        if (washRoom==null){
            throw new RuntimeException("该房间不存在");
        }
        washDate.setCreateTime(LocalDateTime.now());
        int num = washDateMapper.insertWashDate(washDate);
        if(num==0){
            throw new RuntimeException("添加失败");
        }
    }
}




