package com.xuwen.controller;

import com.xuwen.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class SettingController {

    @Autowired
    private SettingService settingService;

    /**
     * 获取黑名单信息
     * @param page
     * @param pagesize
     * @return
     */
    @PostMapping(value = "/blacklist")
    public ResponseEntity findBlackList(int page, int pagesize) {
        return settingService.findBlackList(page,pagesize);
    }

    /**
     * 移除黑名单
     * @param id
     * @return
     */
    @DeleteMapping(value = "/blacklist/{uid}")
    public ResponseEntity delBlackList(@PathVariable("uid") int id) {
        return null;
    }

}
