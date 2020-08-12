package com.czxy.xuecheng.manage_cms.controller;

import com.czxy.xuecheng.api.cms.CmsPageControllerApi;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.domain.cms.CmsPage;
import com.czxy.xuecheng.domain.cms.request.QueryPageRequest;
import com.czxy.xuecheng.domain.cms.response.CmsPageResult;
import com.czxy.xuecheng.domain.cms.response.CmsPostPageResult;
import com.czxy.xuecheng.manage_cms.service.PageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by liangtong.
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Resource
    private PageService pageService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<CmsPage> findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {

/*        //模拟数据 QueryResponseResult 查询响应对象：响应状态 + 响应数据
        //1 数据
        QueryResult<CmsPage> queryResult =new QueryResult<>();
        List<CmsPage> list = new ArrayList<>();
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试页面");
        list.add(cmsPage);
        queryResult.setList(list);
        queryResult.setTotal(1);

        //2 设置状态
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;*/

        return pageService.findList(page,size,queryPageRequest);

    }

    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }

    @GetMapping("/get/{id}")
    public CmsPageResult findById(@PathVariable("id") String id) {
        return pageService.findById(id);
    }

    @PutMapping("/edit/{id}")
    public CmsPageResult update(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        return pageService.update(id,cmsPage);
    }

    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        return pageService.delete(id);
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId") String pageId) {
        return pageService.postPage(pageId);
    }

    @Override
    @PostMapping("/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage) {
        return pageService.save(cmsPage);
    }

    @Override
    @PostMapping("/postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage) {
        return pageService.postPageQuick(cmsPage);
    }
}
