package com.xuwen.api;

import com.xuwen.pojo.db.Announcement;
import com.xuwen.pojo.vo.PageResult;

public interface AnnouncementApi {
    PageResult<Announcement> findPage(int page, int pagesize);
}
