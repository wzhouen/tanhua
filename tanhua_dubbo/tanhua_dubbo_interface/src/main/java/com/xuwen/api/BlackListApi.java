package com.xuwen.api;

import com.xuwen.pojo.db.UserInfo;
import com.xuwen.pojo.vo.PageResult;

public interface BlackListApi {
    PageResult<UserInfo> findListById(int page, int pagesize, Long userId);
}
