package com.xuwen.api;

import com.xuwen.pojo.mongo.RecommendUser;
import com.xuwen.pojo.vo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 *
 */
@Service
public class RecommendUserApiImpl implements RecommendUserApi {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询mongoDB表中推荐值最高的用户
     * @param toUserId
     * @return
     */
    @Override
    public RecommendUser queryWithMaxScore(Long toUserId) {
        Criteria criteria = Criteria.where("toUserId").is(toUserId);

        Query query = new Query(criteria).with(Sort.by(Sort.Order.desc("score"))).limit(1);
        return mongoTemplate.findOne(query,RecommendUser.class);
    }

    @Override
    public List<RecommendUser> findList(Integer page, Integer pagesize, Long toUserId) {
        Criteria criteria = Criteria.where("toUserId").is(toUserId);
        Query query = new Query(criteria).with(Sort.by(Sort.Order.desc("score")));
        query.with(PageRequest.of(page-1,pagesize));
        List<RecommendUser> recommendUsers = mongoTemplate.find(query, RecommendUser.class);
        return recommendUsers;
    }

    @Override
    public Long findCount(Long toUserId) {
        Criteria criteria = Criteria.where("toUserId").is(toUserId);
        Query query = new Query(criteria);
        long count = mongoTemplate.count(query, RecommendUser.class);
        return count;
    }


}
