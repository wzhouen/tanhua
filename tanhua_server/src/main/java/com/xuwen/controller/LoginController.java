package com.xuwen.controller;

import com.xuwen.pojo.db.UserInfo;
import com.xuwen.pojo.vo.UserInfoVo;
import com.xuwen.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/findUser",method = RequestMethod.GET)
    public ResponseEntity findUser(String mobile){
        return userService.findByMobile(mobile);
    }

    /**
     * 新增用户
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody Map<String,Object> param){
        String mobile = (String)param.get("mobile");
        String password = (String)param.get("password");
        return userService.saveUser(mobile,password);
    }

    /**
     * 用户登陆发送验证码
     * @param mobile
     * @return
     */
    @RequestMapping("/login")
    public ResponseEntity login(String mobile){
        userService.login(mobile);
        return ResponseEntity.ok(null);
    }

    /**
     * 创建或登录用户
     * @param mobile
     * @param verificationCode
     * @return
     */
    @RequestMapping("/loginVerification")
    public ResponseEntity loginVerification(String mobile,String verificationCode){
        Map<String, Object> map = userService.loginVerification(mobile, verificationCode);
        return ResponseEntity.ok(map);
    }

    /**
     * 完善用户信息
     * @param userInfoVo
     * @return
     */
    @PostMapping("/loginReginfo")
    public ResponseEntity loginReginfo(@RequestBody UserInfoVo userInfoVo) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoVo,userInfo);
        userService.loginReginfo(userInfo);
        return ResponseEntity.ok(null);
    }

    /**
     * 上传用户头像
     * @param multipartFile
     * @return
     */
    @PostMapping("/loginReginfo/head")
    public ResponseEntity uploadAvatar(MultipartFile multipartFile){
        userService.uploadAvatar(multipartFile);
        return ResponseEntity.ok(null);
    }



}
