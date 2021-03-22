package com.xuwen.api;

import com.xuwen.pojo.db.User;

public interface UserApi {

    /**
     * 添加用户
     * @param user
     * @return
     */
    Long save(User user);

    /**
     * 通过手机号码查询
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

}
