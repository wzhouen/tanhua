package com.xuwen.interceptor;

import com.xuwen.pojo.db.User;

public class UserHolder {

    public static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static User getUser(){
        return threadLocal.get();
    }

    public static void setUser(User user){
        threadLocal.set(user);
    }

    public static Long getUserId(){
        return getUser().getId();
    }
}
