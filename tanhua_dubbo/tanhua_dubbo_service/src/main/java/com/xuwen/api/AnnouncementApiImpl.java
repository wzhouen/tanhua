package com.xuwen.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuwen.dao.AnnouncementDao;
import com.xuwen.pojo.db.Announcement;
import com.xuwen.pojo.vo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AnnouncementApiImpl implements AnnouncementApi {

    @Autowired
    private AnnouncementDao announcementDao;

    @Override
    public PageResult<Announcement> findPage(int page, int pagesize) {
        Page pg = new Page(page, pagesize);
        IPage iPage = announcementDao.selectPage(pg, new QueryWrapper<>());
        PageResult<Announcement> result = new PageResult<>();
        result.setPages(iPage.getPages());
        result.setCounts(iPage.getTotal());
        result.setPagesize(iPage.getSize());
        result.setPage(iPage.getCurrent());
        result.setItems(iPage.getRecords());
        return result;
    }
}
