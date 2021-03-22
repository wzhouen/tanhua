package com.xuwen.api;

import com.xuwen.pojo.mongo.*;
import com.xuwen.pojo.vo.PageResult;
import com.xuwen.pojo.vo.PublishVo;
import org.apache.dubbo.config.annotation.Service;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublishApiImpl implements PublishApi {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存发布的动态
     * @param publishVo
     */
    @Override
    public void add(PublishVo publishVo) {
        //1 保存到发布表 quanzi_publish

        Publish publish = new Publish();
        BeanUtils.copyProperties(publishVo, publish);
        publish.setId(ObjectId.get());
        publish.setSeeType(1);
        publish.setLocationName(publishVo.getLocation());
        publish.setCreated(System.currentTimeMillis());
        mongoTemplate.save(publish);

        //2 保存到相册表  quanzi_album_{userId}
        Album album = new Album();
        album.setId(ObjectId.get());
        album.setPublishId(publish.getId());
        album.setCreated(publish.getCreated());
        mongoTemplate.save(album,"quanzi_album_"+publish.getUserId());

        //3 查询好友表 tanhua_users
        Query query = new Query(Criteria.where("userId").is(publish.getUserId()));
        List<Friend> friends = mongoTemplate.find(query, Friend.class);

        //4 保存到时间线表 quanzi_time_line_{userId}
        for (Friend friend : friends) {
            TimeLine timeLine = new TimeLine();
            timeLine.setId(ObjectId.get());
            timeLine.setUserId(publish.getUserId());
            timeLine.setPublishId(publish.getId());
            timeLine.setCreated(publish.getCreated());
            mongoTemplate.save(timeLine,"quanzi_time_line_"+friend.getFriendId());
        }
    }

    /**
     * 查询好友动态
     * @param page
     * @param pagesize
     * @param userId
     * @return
     */
    @Override
    public PageResult findFriendPublishByTimeline(int page, int pagesize, Long userId) {
        Query query = new Query().with(Sort.by(Sort.Order.desc("created"))).limit(pagesize).skip((page-1)*pagesize);
        List<TimeLine> timeLines = mongoTemplate.find(query, TimeLine.class, "quanzi_time_line_" + userId);
        long count = mongoTemplate.count(query, TimeLine.class, "quanzi_time_line_" + userId);
        List<Publish> publishes = new ArrayList<>();
        for (TimeLine timeLine : timeLines) {
            Publish publish = mongoTemplate.findById(timeLine.getPublishId(), Publish.class);
            publishes.add(publish);
        }
        PageResult<Publish> pageResult = new PageResult<>();
        pageResult.setPage((long) page);
        pageResult.setPagesize((long)pagesize);
        pageResult.setCounts(count);
        long pages = count % pagesize > 0 ? count / pagesize + 1 : count / pagesize ;
        pageResult.setPages(pages);
        pageResult.setItems(publishes);
        return pageResult;
    }
}
