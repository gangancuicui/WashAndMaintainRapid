package com.washmaintance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.washmaintance.pojo.Banner;
import com.washmaintance.pojo.PageBean;
import com.washmaintance.service.BannerService;
import com.washmaintance.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author liang
* @description 针对表【banner】的数据库操作Service实现
* @createDate 2024-05-29 21:52:50
*/
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner>
    implements BannerService{
    @Autowired
    private BannerMapper bannerMapper;
    @Override
    public PageBean getBanner(Integer page, Integer pageSize, Integer status, String note) {
        PageBean pageBean =new PageBean();
        PageHelper.startPage(page,pageSize);
        List<Banner>list=bannerMapper.getBanner(status,note);
        PageInfo<Banner> p = new PageInfo<Banner>(list);
        pageBean =new PageBean((int) p.getTotal(),list);
        return pageBean;
    }

    @Override
    public List<Banner> getIndexBanner() {
        List<Banner> list=bannerMapper.getIndexBanner();
        return list;
    }

    @Override
    public void insertBanner(Banner banner) {
        if(banner.getUrl()==null || banner.getStatus()==null){
            throw new RuntimeException("参数不能为空");
        }
        banner.setCreateTime(LocalDateTime.now());
        int num=bannerMapper.insertBanner(banner);
        if(num!=1){
            throw new RuntimeException("添加失败");
        }
    }

    @Override
    public void updateBanner(Banner banner) {
        if(banner.getId()==null){
            throw new RuntimeException("参数不能为空");
        }
        int num=bannerMapper.updateBanner(banner);
        if (num==0){
            throw new RuntimeException("更新失败");
        }
    }
}




