package com.xuwen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//MapperScan 用于扫描指定包下的所有的接口，将接口产生代理对象交给spring容器
@MapperScan("com.xuwen.dao")
public class DubboServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboServiceApplication.class,args);
    }
}
