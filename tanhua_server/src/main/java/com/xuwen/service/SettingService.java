package com.xuwen.service;

import com.xuwen.api.BlackListApi;
import com.xuwen.interceptor.UserHolder;
import com.xuwen.pojo.db.UserInfo;
import com.xuwen.pojo.vo.PageResult;
import org.apache.dubbo.config.annotation.Reference;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SettingService {

    @Reference
    private BlackListApi blackListApi;

    /**
     * 获取黑名单
     * @param page
     * @param pagesize
     * @return
     */
    public ResponseEntity findBlackList(int page, int pagesize) {
        Long userId = UserHolder.getUserId();
        PageResult<UserInfo> pageResult = blackListApi.findListById(page, pagesize, userId);
        return ResponseEntity.ok(pageResult);
    }
}
