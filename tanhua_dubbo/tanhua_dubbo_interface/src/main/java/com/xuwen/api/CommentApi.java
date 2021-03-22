package com.xuwen.api;

import com.xuwen.pojo.mongo.Comment;

public interface CommentApi {
    long save(Comment comment);

    long remove(Comment comment);
}
