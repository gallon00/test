package com.czxy.xuecheng.manage_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * Created by liangtong.
 */
@SpringBootApplication
@EntityScan("com.czxy.xuecheng.domain.course")                     //扫描实体类
@ComponentScan(basePackages = "com.czxy.xuecheng.common")      //通用接口
@ComponentScan(basePackages = "com.czxy.xuecheng.api")      //扫描接口
@ComponentScan(basePackages = "com.czxy.xuecheng.utils")        //工具
@ComponentScan(basePackages = "com.czxy.xuecheng.manage_course")  //扫描本项目下的所有类
@EnableEurekaClient                     //Eureka的客户端
@EnableFeignClients                     //开始feignClient
public class ManageCourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCourseApplication.class,args);
    }
}
