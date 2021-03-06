package com.xuwen.service;

import com.alibaba.fastjson.JSON;
import com.xuwen.api.UserApi;
import com.xuwen.api.UserInfoApi;
import com.xuwen.exception.TanHuaException;
import com.xuwen.interceptor.UserHolder;
import com.xuwen.pojo.db.User;
import com.xuwen.pojo.db.UserInfo;
import com.xuwen.pojo.vo.ErrorResult;
import com.xuwen.templates.FaceTemplate;
import com.xuwen.templates.HuanXinTemplate;
import com.xuwen.templates.OssTemplate;
import com.xuwen.templates.SmsTemplate;
import com.xuwen.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.dubbo.config.annotation.Reference;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
@Transactional
public class UserService {

    @Reference
    private UserApi userApi;
    @Reference
    private UserInfoApi userInfoApi;
    @Autowired
    private SmsTemplate smsTemplate;
    @Autowired
    private OssTemplate ossTemplate;
    @Autowired
    private FaceTemplate faceTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HuanXinTemplate huanXinTemplate;
    @Autowired
    private JedisPool jedisPool;

    public ResponseEntity findByMobile(String mobile) {
        User user = userApi.findByMobile(mobile);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity saveUser(String mobile, String password) {
        User user = new User();
        user.setMobile(mobile);
        user.setPassword(password);
        userApi.save(user);
        return ResponseEntity.ok(null);
    }

    /**
     * ???????????????
     * @param mobile
     */
    public void login(String mobile) {
        String codeInRedis = jedisPool.getResource().get(mobile);
        Map<String, String> stringStringMap = null;
        if (codeInRedis != null){
            stringStringMap = smsTemplate.sendValidateCode(mobile, codeInRedis);
            if (stringStringMap == null){
                throw new TanHuaException(ErrorResult.fail());
            }
        }else {
            String validateCode = RandomStringUtils.randomNumeric(6);
            stringStringMap = smsTemplate.sendValidateCode(mobile, validateCode);
            if (stringStringMap == null){
                throw new TanHuaException(ErrorResult.fail());
            }
            jedisPool.getResource().set(mobile,validateCode);
        }

    }

    /**
     * ?????????????????????
     * @param mobile
     * @param verificationCode
     * @return
     */
    public Map<String, Object> loginVerification(String mobile, String verificationCode) {
        //1 ?????????????????????????????? ???????????????????????????
        String codeInRedis = jedisPool.getResource().get(mobile);
//        jedisPool.getResource().del(mobile);
        redisTemplate.delete(mobile);// ??????????????????
        if (codeInRedis == null){
            throw new TanHuaException(ErrorResult.loginError());
        }
        //2 ??????????????? ?????????????????????????????????
        if (!codeInRedis.equalsIgnoreCase(verificationCode)){
            throw new TanHuaException(ErrorResult.validateCodeError());
        }
        //3 ??????????????????????????? ?????? ????????????
        User user = userApi.findByMobile(mobile);
        boolean isNew = false;
        if (user == null){
            isNew = true;
            user = new User();
            user.setMobile(mobile);
            user.setCreated(new Date());
            user.setUpdated(new Date());
            user.setPassword(DigestUtils.md5Hex(mobile.substring(mobile.length()-6)));
            Long userId = userApi.save(user);
            user.setId(userId);

            //????????????????????????????????????
            huanXinTemplate.register(user.getId());
        }
        //4 ????????????????????????jwt key???token value???user
        String token = JwtUtils.createJWT(mobile, user.getId());
        UserHolder.setUser(user);
//        jedisPool.getResource().set("TOKEN_"+token, JSON.toJSONString(user));
        redisTemplate.opsForValue().set("TOKEN_" + token,JSON.toJSONString(user),1, TimeUnit.DAYS);
        //5 ??????map
        Map<String,Object> map = new HashMap<>();
        map.put("isNew",isNew);
        map.put("token",token);
        return map;
    }

    /**
     * ??????????????????
     * @param userInfo
     */
    public void loginReginfo(UserInfo userInfo) {

        userInfo.setId(UserHolder.getUserId());
        userInfoApi.save(userInfo);
    }

    /**
     * ??????????????????
     * @param multipartFile
     */
    public void uploadAvatar(MultipartFile multipartFile) {
        try {

            String avatarName = multipartFile.getOriginalFilename();
            avatarName = UUID.randomUUID() + avatarName.substring(avatarName.lastIndexOf(".")+1);
            boolean flag = faceTemplate.detect(multipartFile.getBytes());
            if (!flag){
                throw new TanHuaException("????????????????????????????????????");
            }
            ossTemplate.upload(avatarName,multipartFile.getInputStream());
            UserInfo userInfo = new UserInfo();
            userInfo.setId(UserHolder.getUserId());
            userInfo.setAvatar(avatarName);
            userInfoApi.update(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TanHuaException("????????????????????????????????????");
        }
    }

    /**
     * ??????token??????????????????
     * @param token
     * @return
     */
    public User getUserByToken(String token){
        String key = "TOKEN_" + token;
        String userJson = (String) redisTemplate.opsForValue().get(key);
        if (userJson == null){
            return null;
        }
        //??????????????????
        redisTemplate.expire(key,1, TimeUnit.DAYS);
        User user = JSON.parseObject(userJson, User.class);
        return user;
    }
}
