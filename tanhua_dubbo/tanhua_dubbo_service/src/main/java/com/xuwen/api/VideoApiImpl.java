package com.xuwen.api;

import com.xuwen.pojo.mongo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class VideoApiImpl implements VideoApi {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Video video) {
        mongoTemplate.save(video);
    }
}
