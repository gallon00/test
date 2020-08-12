package com.czxy.xuecheng.api.cms;

import com.czxy.xuecheng.domain.cms.CmsConfig;
import com.czxy.xuecheng.domain.cms.response.CmsConfigResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by liangtong.
 */
@Api(tags="cms配置管理接口",description = "cms配置管理接口，提供数据模型的管理、查询接口")
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询CMS配置信息")
    public CmsConfig getmodel(String id);

}