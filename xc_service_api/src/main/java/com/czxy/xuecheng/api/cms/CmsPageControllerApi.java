package com.czxy.xuecheng.api.cms;


import com.czxy.xuecheng.common.model.response.CommonCode;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.domain.cms.CmsPage;
import com.czxy.xuecheng.domain.cms.request.QueryPageRequest;
import com.czxy.xuecheng.domain.cms.response.CmsCode;
import com.czxy.xuecheng.domain.cms.response.CmsPageResult;
import com.czxy.xuecheng.domain.cms.response.CmsPostPageResult;
import io.swagger.annotations.*;

@Api(tags="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    //页面查询
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    //新增页面
    @ApiOperation("新增页面")
    public CmsPageResult add(CmsPage cmsPage);

    //根据页面id查询页面信息
    @ApiOperation("根据页面id查询页面信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "唯一标识",required=true,paramType="path",dataType="int")
    })
    public CmsPageResult findById(String id);

    //修改页面
    @ApiOperation("修改页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "唯一标识",required=true,paramType="path",dataType="int")
    })
    public CmsPageResult update(String id,CmsPage cmsPage);


    @ApiOperation("删除页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "唯一标识",required=true,paramType="path",dataType="int")
    })
    @ApiResponses({
            @ApiResponse(code = 24006 ,message = "页面不存在",response = CmsCode.class),
            @ApiResponse(code = 10000 ,message = "操作成功",response = CommonCode.class),
    })
    public ResponseResult delete(String id);

    @ApiOperation("发布页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "唯一标识",required=true,paramType="path",dataType="int")
    })
    public ResponseResult post(String pageId);

    @ApiOperation("保存页面")
    public CmsPageResult save(CmsPage cmsPage);

    @ApiOperation("一键发布页面")
    public CmsPostPageResult postPageQuick(CmsPage cmsPage);

}
