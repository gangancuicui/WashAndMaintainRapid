package com.washmaintance.service;


import com.washmaintance.pojo.Manager;
import com.washmaintance.pojo.PageBean;

import java.time.LocalDate;
import java.time.LocalTime;

/**
* @author liang
* @description 针对表【mannager】的数据库操作Service
* @createDate 2024-05-22 15:08:07
*/
public interface ManagerService {

   PageBean getAllOrder(LocalDate washDateStart,
                        LocalDate washDateEnd,
                        String washRoomName,
                        LocalTime washDatetimeStart,
                        LocalTime washDatetimeEnd,
                        Integer washStatus,
                        String washName,
                        String washId,
                        Integer page,
                        Integer pageSize);

    String managerLogin(Manager manager);
}
