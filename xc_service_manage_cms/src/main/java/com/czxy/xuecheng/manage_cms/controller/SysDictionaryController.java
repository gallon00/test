package com.czxy.xuecheng.manage_cms.controller;

import com.czxy.xuecheng.api.system.SysDictionaryControllerApi;
import com.czxy.xuecheng.domain.system.response.SysDictionaryResult;
import com.czxy.xuecheng.manage_cms.service.SysDictionaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by liangtong.
 */
@RestController
@RequestMapping("/sys/dictionary")
public class SysDictionaryController implements SysDictionaryControllerApi {

    @Resource
    private SysDictionaryService sysDictionaryService;

    @GetMapping("/get/{dType}")
    public SysDictionaryResult findByType(@PathVariable("dType") String dType){
        return sysDictionaryService.findByType(dType);
    }

}
