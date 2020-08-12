package com.czxy.xuecheng.manage_course.feign;

import com.czxy.xuecheng.domain.cms.CmsPage;
import com.czxy.xuecheng.domain.cms.response.CmsPageResult;
import com.czxy.xuecheng.domain.cms.response.CmsPostPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by liangtong.
 */
@FeignClient(value = "xc-service-manage-cms", path="/cms/page")
public interface CmsPageFeign {

    @PostMapping("/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage);

    @PostMapping("/postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage);
}
