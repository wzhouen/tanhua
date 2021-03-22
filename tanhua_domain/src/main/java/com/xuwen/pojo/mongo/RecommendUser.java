package com.xuwen.pojo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 存在mongoDB中的表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("recommend_user")
public class RecommendUser implements Serializable {

    @Id
    private ObjectId id; //主键id
    @Indexed
    private Long userId; //推荐的用户id
    private Long toUserId; //用户id
    @Indexed
    private Double score =0d; //推荐得分
    private String date; //日期
}
