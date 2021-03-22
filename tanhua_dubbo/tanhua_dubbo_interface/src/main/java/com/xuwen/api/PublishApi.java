package com.xuwen.api;

import com.xuwen.pojo.vo.PageResult;
import com.xuwen.pojo.vo.PublishVo;

public interface PublishApi {
    void add(PublishVo publishVo);

    PageResult findFriendPublishByTimeline(int page, int pagesize, Long userId);
}
