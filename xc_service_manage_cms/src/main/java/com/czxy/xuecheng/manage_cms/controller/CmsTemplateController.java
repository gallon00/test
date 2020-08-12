package com.czxy.xuecheng.manage_cms.controller;

import com.czxy.xuecheng.api.cms.CmsTemplateControllerApi;
import com.czxy.xuecheng.common.model.response.CommonCode;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.domain.cms.CmsSite;
import com.czxy.xuecheng.domain.cms.CmsTemplate;
import com.czxy.xuecheng.domain.cms.response.CmsTemplateResult;
import com.czxy.xuecheng.manage_cms.service.PageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Created by liangtong.
 */
@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi{

    @Resource
    private PageService pageService;

    /**
     * 查询所有模板
     * @return
     */
    @Override
    @GetMapping
    public QueryResponseResult<CmsTemplate> findAll(){
        return pageService.findAllTemplate();
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public CmsTemplateResult upload(MultipartFile file){
        /*System.out.println(file);
        CmsTemplate cmsTemplate = new CmsTemplate();
        cmsTemplate.setTemplateFileId("123abc");
        return new CmsTemplateResult(CommonCode.SUCCESS,cmsTemplate);*/
        return pageService.upload(file);
    }

    @Override
    @PostMapping("/add")
    public CmsTemplateResult add(@RequestBody CmsTemplate cmsTemplate) {
        return pageService.addTemplate(cmsTemplate);
    }
}
