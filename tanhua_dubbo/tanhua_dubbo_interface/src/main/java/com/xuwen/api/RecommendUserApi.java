package com.xuwen.api;

import com.xuwen.pojo.mongo.RecommendUser;

import java.util.List;

public interface RecommendUserApi {

    /**
     * 查询mongoDB表中推荐值最高的信息
     */
    RecommendUser queryWithMaxScore(Long toUserId);

    /**
     * 查询mongo表中指定条数的信息
     * @param page
     * @param pagesize
     * @param toUserId
     * @return
     */
    List<RecommendUser> findList(Integer page, Integer pagesize, Long toUserId);

    Long findCount(Long toUserId);
}