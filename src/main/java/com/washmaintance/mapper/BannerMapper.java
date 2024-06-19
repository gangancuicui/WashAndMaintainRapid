package com.washmaintance.mapper;

import com.washmaintance.pojo.Banner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liang
* @description 针对表【banner】的数据库操作Mapper
* @createDate 2024-05-29 21:52:50
* @Entity com.washmaintance.pojo.Banner
*/
@Mapper
public interface BannerMapper extends BaseMapper<Banner> {

    List<Banner> getBanner(Integer status, String note);

    @Select("select url from banner where status = 1")
    List<Banner> getIndexBanner();

    @Insert("insert into banner(url,status,note,create_time) values(#{url},#{status},#{note},#{createTime})")
    int insertBanner(Banner banner);

    int updateBanner(Banner banner);
}




