package com.xuwen.service;

import com.xuwen.api.CommentApi;
import com.xuwen.interceptor.UserHolder;
import com.xuwen.pojo.mongo.Comment;
import org.apache.dubbo.config.annotation.Reference;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MomentService {

    @Reference
    private CommentApi commentApi;
    @Autowired
    private RedisTemplate redisTemplate;


    public ResponseEntity like(String publishId) {
        Comment comment = new Comment();
        comment.setId(new ObjectId());
        comment.setPublishId(new ObjectId(publishId));
        comment.setUserId(UserHolder.getUserId());
        comment.setCommentType(1);
        comment.setPubType(1);
        comment.setCreated(System.currentTimeMillis());
        long likeCount = commentApi.save(comment);
        String key = "publish_like_" + UserHolder.getUserId()+"_" + publishId;
        redisTemplate.opsForValue().set(key,"1");
        return ResponseEntity.ok(likeCount);
    }

    public ResponseEntity unlike(String publishId) {
        Comment comment = new Comment();
        comment.setId(new ObjectId());
        comment.setPublishId(new ObjectId(publishId));
        comment.setUserId(UserHolder.getUserId());
        comment.setCommentType(1);
        comment.setPubType(1);
        comment.setCreated(System.currentTimeMillis());
        long likeCount = commentApi.remove(comment);
        String key = "publish_like_" + UserHolder.getUserId()+"_" + publishId;
        redisTemplate.delete(key);
        return ResponseEntity.ok(likeCount);
    }
}
