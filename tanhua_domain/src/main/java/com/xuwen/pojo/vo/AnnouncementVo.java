package com.xuwen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementVo {
    private String id;
    private String title;
    private String description;
    private Date createDate;
}