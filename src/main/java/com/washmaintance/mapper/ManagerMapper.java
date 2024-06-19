package com.washmaintance.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.washmaintance.pojo.Manager;
import com.washmaintance.pojo.WashOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liang
* @description 针对表【mannager】的数据库操作Mapper
* @createDate 2024-05-22 15:08:07
* @Entity com.washmaintance.washmaintance.pojo.Mannager
*/
@Mapper
public interface ManagerMapper extends BaseMapper<Manager> {

    @Select("select * from manager where username = #{username} and password = #{password}")
    public Manager getManager(Manager manager);


}




