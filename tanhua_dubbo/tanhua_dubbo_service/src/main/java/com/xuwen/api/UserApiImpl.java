package com.xuwen.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuwen.dao.UserDao;
import com.xuwen.pojo.db.User;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserApiImpl implements UserApi {

    @Autowired
    private UserDao userDao;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public Long save(User user) {
        userDao.insert(user);
        return user.getId();
    }

    /**
     * 通过手机号码查询
     * @param mobile
     * @return
     */
    @Override
    public User findByMobile(String mobile) {
        QueryWrapper<User> queryMapper = new QueryWrapper<>();
        queryMapper.eq("mobile",mobile);
        return userDao.selectOne(queryMapper);
    }
}
