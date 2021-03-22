package com.xuwen.api;

import com.xuwen.dao.UserInfoDao;
import com.xuwen.pojo.db.UserInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TodayBestApiImpl implements TodayBestApi {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findUserInfoById(long id) {
        return userInfoDao.selectById(id);
    }
}
