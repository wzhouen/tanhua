package com.xuwen.interceptor;

import com.xuwen.pojo.db.User;
import com.xuwen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //1 通过request获取头信息 获得token
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)){
            response.setStatus(401);
            return false;
        }
        //2 通过token 获取 user 信息
        User user = userService.getUserByToken(token);
        if (StringUtils.isEmpty(user)){
            response.setStatus(401);
            return false;
        }
        //3 将user信息存进ThreadLocal中
        UserHolder.setUser(user);
        return true;
    }
}
