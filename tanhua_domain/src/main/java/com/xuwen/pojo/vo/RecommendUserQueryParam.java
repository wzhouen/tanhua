package com.xuwen.pojo.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * 对应前端传来的参数
 */
@Data
public class RecommendUserQueryParam implements Serializable {

    private Integer page;
    private Integer pagesize;
    private String gender;
    private String lastLogin;
    private Integer age;
    private String city;
    private String education;
}