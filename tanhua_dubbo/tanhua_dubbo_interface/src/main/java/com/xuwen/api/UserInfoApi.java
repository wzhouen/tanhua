package com.xuwen.api;

import com.xuwen.pojo.db.UserInfo;

public interface UserInfoApi {
    /**
     * 保存用户基础信息
     * @param userInfo
     */
    void save(UserInfo userInfo);

    /**
     * 通过id更新用户信息
     * @param userInfo
     */
    void update(UserInfo userInfo);

    /**
     * 通过id查询用户信息
     * @param userId
     * @return
     */
    UserInfo findOne(Long userId);
}