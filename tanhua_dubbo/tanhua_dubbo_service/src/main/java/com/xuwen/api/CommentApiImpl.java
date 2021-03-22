package com.xuwen.api;

import com.xuwen.pojo.mongo.Comment;
import com.xuwen.pojo.mongo.Publish;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@Service
public class CommentApiImpl implements CommentApi {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long save(Comment comment) {
        //保存
        mongoTemplate.save(comment);
        //更新点赞数
        uploadCount(comment,1);
        //获取点赞数
        long count = getCount(comment);
        return count;
    }

    @Override
    public long remove(Comment comment) {
        Query query = new Query(Criteria
                .where("publicId").is(comment.getPublishId())
                .and("commentType").is(comment.getCommentType())
                .and("pubType").is(comment.getPubType())
                .and("userId").is(comment.getUserId()));

        mongoTemplate.remove(query,Comment.class);
        uploadCount(comment, -1);
        long count = getCount(comment);
        return count;
    }

    private void uploadCount(Comment comment, int value) {
        if (comment.getPubType() == 1){
            Query query = new Query(Criteria.where("id").is(comment.getPublishId()));
            Update update = new Update();
            update.inc(comment.getCol(),value);
            mongoTemplate.updateFirst(query,update,Publish.class);
        }
        if (comment.getPubType() == 3){
            Query query = new Query(Criteria.where("id").is(comment.getPublishId()));
            Update update = new Update();
            update.inc(comment.getCol(),value);
            mongoTemplate.updateFirst(query,update,Comment.class);
        }
    }

    private long getCount(Comment comment) {
        Query query = new Query(Criteria.where("id").is(comment.getPublishId()));
        Publish publish = mongoTemplate.findOne(query, Publish.class);
        if(comment.getCommentType() == 1){
            return publish.getLikeCount();
        }
        if(comment.getCommentType() == 2){// //评论类型，1-点赞，2-评论，3-喜欢
            return publish.getCommentCount();
        }
        if(comment.getCommentType() == 3){
            return publish.getLoveCount();
        }
        return 99;
    }
}
