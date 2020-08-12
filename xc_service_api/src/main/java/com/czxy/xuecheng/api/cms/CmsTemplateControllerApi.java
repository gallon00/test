package com.czxy.xuecheng.api.cms;

import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.domain.cms.CmsTemplate;
import com.czxy.xuecheng.domain.cms.response.CmsTemplateResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by liangtong.
 */
@Api(tags = "cms模板管理接口", description = "提供模板的增、删、改、查操作")
public interface CmsTemplateControllerApi {

    @ApiOperation("查询所有模板")
    public QueryResponseResult<CmsTemplate> findAll();

    @ApiOperation("模板文件上传")
    public CmsTemplateResult upload(MultipartFile file);

    @ApiOperation("添加模板")
    public CmsTemplateResult add(CmsTemplate cmsTemplate);


}
