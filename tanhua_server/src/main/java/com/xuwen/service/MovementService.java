package com.xuwen.service;

import com.xuwen.api.PublishApi;
import com.xuwen.api.UserInfoApi;
import com.xuwen.interceptor.UserHolder;
import com.xuwen.pojo.db.UserInfo;
import com.xuwen.pojo.mongo.Publish;
import com.xuwen.pojo.utils.RelativeDateFormat;
import com.xuwen.pojo.vo.MomentVo;
import com.xuwen.pojo.vo.PageResult;
import com.xuwen.pojo.vo.PublishVo;
import com.xuwen.templates.OssTemplate;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MovementService {

    @Reference
    private PublishApi publishApi;
    @Reference
    private UserInfoApi userInfoApi;
    @Autowired
    private OssTemplate ossTemplate;

    /**
     * 发布动态
     * @param publishVo
     * @param imageContent
     * @return
     * @throws IOException
     */
    public void save(PublishVo publishVo, MultipartFile[] imageContent) throws IOException {
        List<String> medias = new ArrayList<>();
        for (MultipartFile multipartFile : imageContent) {
            String url = ossTemplate.upload(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
            medias.add(url);
        }
        publishVo.setUserId(UserHolder.getUserId());
        publishVo.setMedias(medias);
        publishApi.add(publishVo);
    }

    /**
     * 查询好友动态
     *
     * @return
     */
    public PageResult<MomentVo> queryFriendPublishList(int page, int pagesize) {
        Long userId = UserHolder.getUserId();
        PageResult pageResult = publishApi.findFriendPublishByTimeline(page, pagesize, userId);
        List<Publish> publishes = pageResult.getItems();
        List<MomentVo> momentVoList = new ArrayList<>();
        for (Publish publish : publishes) {
            MomentVo momentVo = new MomentVo();
            UserInfo info = userInfoApi.findOne(publish.getUserId());
            BeanUtils.copyProperties(info,momentVo);
            momentVo.setTags(info.getTags().split(","));

            BeanUtils.copyProperties(publish, momentVo);
            momentVo.setId(publish.getId().toHexString());
            momentVo.setImageContent(publish.getMedias().toArray(new String[]{}));
            momentVo.setDistance("距离50m");
            momentVo.setCreateDate(RelativeDateFormat.format(new Date(publish.getCreated())));
            momentVo.setHasLiked(0);  //是否点赞  0：未点 1:点赞
            momentVo.setHasLoved(0);  //是否喜欢  0：未点 1:点赞

            momentVoList.add(momentVo);
        }
        pageResult.setItems(momentVoList);
        return pageResult;

    }
}
