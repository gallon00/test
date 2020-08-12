package com.czxy.xuecheng.manage_cms.controller;

import com.czxy.xuecheng.api.cms.CmsConfigControllerApi;
import com.czxy.xuecheng.domain.cms.CmsConfig;
import com.czxy.xuecheng.domain.cms.response.CmsConfigResult;
import com.czxy.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by liangtong.
 */
@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {

    @Resource
    private PageService pageService;

    @Override
    @GetMapping("/getmodel/{id}")
    public CmsConfig getmodel(@PathVariable("id") String id) {
        return pageService.getConfigById(id);
    }
}
