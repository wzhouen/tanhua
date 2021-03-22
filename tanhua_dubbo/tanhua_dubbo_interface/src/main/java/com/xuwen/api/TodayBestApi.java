package com.xuwen.api;

import com.xuwen.pojo.db.UserInfo;

public interface TodayBestApi {
    UserInfo findUserInfoById(long id);
}
