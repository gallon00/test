package com.czxy.xuecheng.manage_media;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by liangtong.
 */
@SpringBootApplication
@EnableEurekaClient
@EntityScan("com.czxy.xuecheng.domain.media")              //扫描实体类
@ComponentScan(basePackages = "com.czxy.xuecheng.common")      //通用接口
@ComponentScan(basePackages = "com.czxy.xuecheng.api")      //扫描接口
@ComponentScan(basePackages = "com.czxy.xuecheng.utils")        //工具
@ComponentScan(basePackages = "com.czxy.xuecheng.manage_media")  //扫描本项目下的所有类
public class ManageMediaApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ManageMediaApplication.class, args);
    }
}
