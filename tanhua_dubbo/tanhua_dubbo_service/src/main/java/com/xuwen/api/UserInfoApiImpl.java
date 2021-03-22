package com.xuwen.api;

import com.xuwen.dao.UserInfoDao;
import com.xuwen.pojo.db.UserInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserInfoApiImpl implements UserInfoApi {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void save(UserInfo userInfo) {
        userInfoDao.insert(userInfo);
    }

    @Override
    public void update(UserInfo userInfo) {
        userInfoDao.updateById(userInfo);
    }

    @Override
    public UserInfo findOne(Long userId) {
        return userInfoDao.selectById(userId);
    }
}
