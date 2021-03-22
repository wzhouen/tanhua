package com.xuwen.service;

import com.xuwen.api.AnnouncementApi;
import com.xuwen.pojo.db.Announcement;
import com.xuwen.pojo.vo.AnnouncementVo;
import com.xuwen.pojo.vo.PageResult;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AnnouncementService {

    @Reference
    private AnnouncementApi announcementApi;

    public ResponseEntity findPage(int page, int pagesize) {
        PageResult<Announcement> result = announcementApi.findPage(page, pagesize);
        List<Announcement> announcementList = result.getItems();
        List<AnnouncementVo> announcementVoList = new ArrayList<>();
        for (Announcement announcement : announcementList) {
            AnnouncementVo announcementVo = new AnnouncementVo();
            BeanUtils.copyProperties(announcement, announcementVo);
            announcementVoList.add(announcementVo);
        }
        PageResult<AnnouncementVo> resultVo = new PageResult<>();
        resultVo.setPage(result.getPage());
        resultVo.setPagesize(result.getPagesize());
        resultVo.setCounts(result.getCounts());
        resultVo.setItems(announcementVoList);
        return ResponseEntity.ok(resultVo);
    }
}
