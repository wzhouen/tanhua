package com.xuwen.pojo.db;

import lombok.Data;

@Data
public class Question extends BasePojo {
    private Long id;
    private Long userId;
    //问题内容
    private String txt;
}