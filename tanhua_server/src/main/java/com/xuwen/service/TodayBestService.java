package com.xuwen.service;

import com.aliyuncs.utils.StringUtils;
import com.xuwen.api.RecommendUserApi;
import com.xuwen.api.TodayBestApi;
import com.xuwen.api.UserInfoApi;
import com.xuwen.exception.TanHuaException;
import com.xuwen.interceptor.UserHolder;
import com.xuwen.pojo.db.UserInfo;
import com.xuwen.pojo.mongo.RecommendUser;
import com.xuwen.pojo.vo.PageResult;
import com.xuwen.pojo.vo.RecommendUserQueryParam;
import com.xuwen.pojo.vo.TodayBestVo;
import com.xuwen.pojo.vo.UserInfoVo;
import org.apache.commons.lang3.RandomUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TodayBestService {

    @Reference
    private TodayBestApi todayBestApi;
    @Reference
    private RecommendUserApi recommendUserApi;
    @Reference
    private UserInfoApi userInfoApi;

    /**
     * 查询今日佳人
     * @return
     */
    public ResponseEntity todayBest() {
        RecommendUser recommendUser = recommendUserApi.queryWithMaxScore(UserHolder.getUserId());
        if (recommendUser == null){
            recommendUser = new RecommendUser();
            recommendUser.setUserId(2l);
            recommendUser.setScore(95d);
        }
        UserInfo userInfo = todayBestApi.findUserInfoById(recommendUser.getUserId());
        if (userInfo == null){
            throw new TanHuaException("未查询到推荐用户");
        }
        TodayBestVo todayBestVo = new TodayBestVo();
        BeanUtils.copyProperties(userInfo,todayBestVo);
        if(!StringUtils.isEmpty(userInfo.getTags())) {
            todayBestVo.setTags(userInfo.getTags().split(","));
        }
        todayBestVo.setFateValue(recommendUser.getScore().longValue());
        return ResponseEntity.ok(todayBestVo);
    }

    /**
     * 查询今日推荐用户列表
     * @param queryParam
     * @return
     */
    public ResponseEntity recommendation(RecommendUserQueryParam queryParam) {
        List<RecommendUser> recommendUsers = recommendUserApi.findList(queryParam.getPage(), queryParam.getPagesize(), UserHolder.getUserId());
        if (CollectionUtils.isEmpty(recommendUsers)){
            recommendUsers = defaultRecommend();
        }
        List<TodayBestVo> todayBestVos = new ArrayList<>();
        for (RecommendUser recommendUser : recommendUsers) {
            UserInfo userInfo = userInfoApi.findOne(recommendUser.getUserId());
            TodayBestVo todayBestVo = new TodayBestVo();
            BeanUtils.copyProperties(userInfo,todayBestVo);
            todayBestVo.setTags(userInfo.getTags().split(","));
            todayBestVo.setFateValue(recommendUser.getScore().longValue());
            todayBestVos.add(todayBestVo);
        }
        PageResult<TodayBestVo> page = new PageResult<>();
        Long count = recommendUserApi.findCount(UserHolder.getUserId());
        int pages = count / queryParam.getPagesize() + count % queryParam.getPagesize() > 0 ? 1 : 0;
        page.setPage((long)queryParam.getPage());
        page.setPagesize((long) queryParam.getPagesize());
        page.setCounts(count);
        page.setPages((long)pages);
        page.setItems(todayBestVos);
        return ResponseEntity.ok(page);

    }

    //构造默认数据
    private List<RecommendUser> defaultRecommend() {
        String ids = "1,2,3,4,5,6,7,8,9,10";
        List<RecommendUser> records = new ArrayList<>();
        for (String id : ids.split(",")) {
            RecommendUser recommendUser = new RecommendUser();
            recommendUser.setUserId(Long.valueOf(id));
            recommendUser.setScore(RandomUtils.nextDouble(70, 98));
            records.add(recommendUser);
        }
        return records;
    }
}
