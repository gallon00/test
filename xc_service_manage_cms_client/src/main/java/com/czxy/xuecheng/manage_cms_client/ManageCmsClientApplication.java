package com.czxy.xuecheng.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by liangtong.
 */
@SpringBootApplication
@EntityScan("com.czxy.xuecheng.domain.cms") //扫描实体类
@ComponentScan(basePackages={"com.czxy.xuecheng.common"})   //扫描common包下的类
@ComponentScan(basePackages={"com.czxy.xuecheng.manage_cms_client"})    //扫描本项目下的所有类
public class ManageCmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsClientApplication.class,args);
    }
}
