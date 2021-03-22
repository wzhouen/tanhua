package com.xuwen.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuwen.dao.BlackListDao;
import com.xuwen.pojo.db.BlackList;
import com.xuwen.pojo.db.User;
import com.xuwen.pojo.db.UserInfo;
import com.xuwen.pojo.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BlackListApiImpl implements BlackListApi {

    @Autowired
    private BlackListDao blackListDao;

    @Override
    public PageResult<UserInfo> findListById(int page, int pagesize, Long userId) {
        Page<UserInfo> pg = new Page(page, pagesize);
        IPage<UserInfo> pageInfo = blackListDao.findBlackList(pg, userId);
        PageResult<UserInfo> pageResult = new PageResult<>();
        pageResult.setCounts(pageInfo.getTotal());
        pageResult.setItems(pageInfo.getRecords());
        pageResult.setPages(pageInfo.getCurrent());
        pageResult.setPagesize(pageInfo.getSize());
        return pageResult;
    }
}
