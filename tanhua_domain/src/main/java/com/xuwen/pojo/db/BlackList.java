package com.xuwen.pojo.db;

import lombok.Data;

@Data
public class BlackList extends BasePojo {
    private Long id;
    private Long userId;
    private Long blackUserId;
}