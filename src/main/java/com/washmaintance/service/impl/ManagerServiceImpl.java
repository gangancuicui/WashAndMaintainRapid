package com.washmaintance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.washmaintance.mapper.ManagerMapper;
import com.washmaintance.mapper.WashOrderMapper;
import com.washmaintance.pojo.Manager;
import com.washmaintance.pojo.PageBean;
import com.washmaintance.pojo.WashOrder;
import com.washmaintance.pojo.WashRoom;
import com.washmaintance.service.ManagerService;
import com.washmaintance.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author liang
* @description 针对表【mannager】的数据库操作Service实现
* @createDate 2024-05-22 15:08:07
*/
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerMapper   managerMapper;
    @Autowired
    private WashOrderMapper washOrderMapper;

    @Override
    public PageBean getAllOrder(LocalDate washDateStart,
                                LocalDate washDateEnd,
                                String washRoomName,
                                LocalTime washDatetimeStart,
                                LocalTime washDatetimeEnd,
                                Integer washStatus,
                                String washName,
                                String washId,
                                Integer page,
                                Integer pageSize) {
        PageBean pageBean =new PageBean();
        PageHelper.startPage(page,pageSize);
        List<WashOrder> list = washOrderMapper.getAllOrder(washDateStart,
                washDateEnd,washRoomName,washDatetimeStart,
                washDatetimeEnd,washStatus,washName,washId,page,pageSize);
        PageInfo<WashOrder> p = new PageInfo<WashOrder>(list);
        pageBean =new PageBean((int) p.getTotal(),list);
        return pageBean;
    }

    @Override
    public String managerLogin(Manager manager) {
        Manager manager1=managerMapper.getManager(manager);
        if(manager1!=null){
            Map<String, Object> map=new HashMap<>();

            map.put("id",manager1.getId());
            map.put("username",manager1.getUsername());
            return JwtUtils.generateJwt(map);
        }else throw new RuntimeException("账号密码错误");

    }
}




