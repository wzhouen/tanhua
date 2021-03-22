package com.xuwen.controller;

import com.xuwen.pojo.vo.RecommendUserQueryParam;
import com.xuwen.service.TodayBestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tanhua")
public class TodayBestController {

    @Autowired
    private TodayBestService todayBestService;

    /**
     * 查询今日佳人
     * @return
     */
    @GetMapping("/todayBest")
    public ResponseEntity todayBest(){
        return todayBestService.todayBest();
    }

    /**
     * 查询今日推荐用户列表
     * @param queryParam
     * @return
     */
    @GetMapping("/recommendation")
    public ResponseEntity recommendation(RecommendUserQueryParam queryParam){
        return todayBestService.recommendation(queryParam);
    }

}
