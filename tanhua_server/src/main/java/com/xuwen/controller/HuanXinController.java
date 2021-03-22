package com.xuwen.controller;

import com.xuwen.interceptor.UserHolder;
import com.xuwen.vo.HuanXinUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/huanxin")
public class HuanXinController {

    /**
     * 获取当前登陆的用户名与密码，用于环信的登陆
     * @return
     */
    @GetMapping("/user")
    public ResponseEntity<HuanXinUser> getLoginHuanXinUser(){
        HuanXinUser user = new HuanXinUser(UserHolder.getUserId().toString(), "123456",String.format("今晚打老虎_%d",100));
        return ResponseEntity.ok(user);
    }
}