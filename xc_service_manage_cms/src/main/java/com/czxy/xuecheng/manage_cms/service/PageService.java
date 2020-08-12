package com.czxy.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.czxy.xuecheng.common.exception.ExceptionCast;
import com.czxy.xuecheng.common.model.response.CommonCode;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.common.model.response.QueryResult;
import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.domain.cms.CmsConfig;
import com.czxy.xuecheng.domain.cms.CmsPage;
import com.czxy.xuecheng.domain.cms.CmsSite;
import com.czxy.xuecheng.domain.cms.CmsTemplate;
import com.czxy.xuecheng.domain.cms.request.QueryPageRequest;
import com.czxy.xuecheng.domain.cms.response.*;
import com.czxy.xuecheng.manage_cms.config.RabbitmqConfig;
import com.czxy.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.czxy.xuecheng.manage_cms.dao.CmsPageRepository;
import com.czxy.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.czxy.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by liangtong.
 */
@Service
public class PageService {

    @Resource
    private CmsPageRepository cmsPageRepository;

    @Resource
    private CmsSiteRepository cmsSiteRepository;

    @Resource
    private CmsTemplateRepository cmsTemplateRepository;

    /**
     * 页面查询方法
     * @param page 页码，从1开始记数
     * @param size 每页记录数
     * @param queryPageRequest 查询条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
        // 1 自定义条件查询
        // 1.1 查询条件对象非空修复
        if(queryPageRequest == null){
            queryPageRequest = new QueryPageRequest();
        }
        // 1.2 定义条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        // 1.3 条件值对象
        CmsPage cmsPage = new CmsPage();
        // 设置条件值（站点id）
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        // 设置模板id作为查询条件
        if(StringUtils.isNotEmpty(queryPageRequest.getTemplateId())){
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        // 设置页面别名作为查询条件
        if(StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        // 1.4 定义条件对象Example
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        // 1.5 分页参数
        if(page <=0){
            page = 1;
        }
        page = page -1;
        if(size<=0){
            size = 10;
        }
        Pageable pageable = PageRequest.of(page,size);

        //2 查询
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);//实现自定义条件查询并且分页查询

        //3 结果封装：状态 + 数据
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总记录数
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

    /**
     * 查询所有站点
     * @return
     */
    public QueryResponseResult<CmsSite> findAllSite(){
        List<CmsSite> list = this.cmsSiteRepository.findAll();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        return new QueryResponseResult<>(CommonCode.SUCCESS,queryResult);
    }

    /**
     * 查询所有模板
     * @return
     */
    public QueryResponseResult<CmsTemplate> findAllTemplate(){
        List<CmsTemplate> list = this.cmsTemplateRepository.findAll();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        return new QueryResponseResult<>(CommonCode.SUCCESS,queryResult);
    }


    public CmsPageResult add(CmsPage cmsPage) {
        if(cmsPage == null){
            //抛出异常，非法参数异常..指定异常信息的内容
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //校验页面名称、站点Id、页面webpath的唯一性
        //根据页面名称、站点Id、页面webpath去cms_page集合，如果查到说明此页面已经存在，如果查询不到再继续添加
        CmsPage findCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(findCmsPage != null){
            //页面已经存在
            //抛出异常，异常内容就是页面已经存在
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }

        //调用dao新增页面
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    public CmsPageResult findById(String id){
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        return new CmsPageResult(CmsCode.CMS_PAGE_NOTEXISTS,null);
    }

    //修改页面
    public CmsPageResult update(String id,CmsPage cmsPage){
        //根据id从数据库查询页面信息
        CmsPage one = this.findById(id).getCmsPage();
        if(one!=null){
            //准备更新数据
            //设置要修改的数据
            //更新模板id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dataUrl
            one.setDataUrl(cmsPage.getDataUrl());
            //提交修改
            cmsPageRepository.save(one);
            return new CmsPageResult(CommonCode.SUCCESS,one);
        }
        //修改失败
        return new CmsPageResult(CommonCode.FAIL,null);

    }

    public ResponseResult delete(String id) {
        CmsPage one = this.findById(id).getCmsPage();
        if( one == null ) {
            return new ResponseResult(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        cmsPageRepository.deleteById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Resource
    private GridFsTemplate gridFsTemplate;

    public CmsTemplateResult upload(MultipartFile file) {

        try {
            //向GridFS存储文件
            ObjectId objectId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), "");
            //得到文件ID
            CmsTemplate cmsTemplate = new CmsTemplate();
            cmsTemplate.setTemplateFileId(objectId.toString());
            return new CmsTemplateResult(CommonCode.SUCCESS,cmsTemplate);
        } catch (IOException e) {
            return new CmsTemplateResult(CommonCode.FAIL,null);
        }
    }

    public CmsTemplateResult addTemplate(CmsTemplate cmsTemplate) {
        //1.1 非空校验
        if(cmsTemplate == null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //1.2 唯一校验
        CmsTemplate temp = cmsTemplateRepository.findByTemplateName(cmsTemplate.getTemplateName());
        if(temp != null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_EXISTS);
        }

        // 2 添加
        cmsTemplate.setTemplateId(null);
        cmsTemplateRepository.save(cmsTemplate);
        return new CmsTemplateResult(CommonCode.SUCCESS, cmsTemplate);

    }

    @Resource
    private CmsConfigRepository cmsConfigRepository;

    public CmsConfig getConfigById(String id) {
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if(! optional.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        CmsConfig cmsConfig = optional.get();
        return cmsConfig;
        //return new CmsConfigResult(CommonCode.SUCCESS, cmsConfig);
    }


    //页面静态化
    public String getPageHtml(String pageId){
        //获取页面模型数据
        Map model = this.getModelByPageId(pageId);
        if(model == null){
            //获取页面模型数据为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        //获取页面模板
        String templateContent = getTemplateByPageId(pageId);
        if(StringUtils.isEmpty(templateContent)){
            //页面模板为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //执行静态化
        String html = generateHtml(templateContent, model);
        if(StringUtils.isEmpty(html)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;

    }
    //页面静态化
    public String generateHtml(String template,Map model){
        try {
            //生成配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template",template);
            //配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            //获取模板
            Template template1 = configuration.getTemplate("template");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Resource
    private GridFSBucket gridFSBucket;
    //获取页面模板
    public String getTemplateByPageId(String pageId){
        //查询页面信息
        CmsPage cmsPage = this.findById(pageId).getCmsPage();
        if(cmsPage == null){
            //页面不存在
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        //页面模板
        String templateId = cmsPage.getTemplateId();
        if(StringUtils.isEmpty(templateId)){
            //页面模板为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if(optional.isPresent()){
            CmsTemplate cmsTemplate = optional.get();
            //模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //取出模板文件内容
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建GridFsResource
            GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
            try {
                String content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
                return content;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
    @Resource
    private RestTemplate restTemplate;

    //获取页面模型数据
    public Map getModelByPageId(String pageId){
        //查询页面信息
        CmsPage cmsPage = this.findById(pageId).getCmsPage();
        if(cmsPage == null){
            //页面不存在
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        //取出dataUrl
        String dataUrl = cmsPage.getDataUrl();
        if(StringUtils.isEmpty(dataUrl)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        ResponseEntity<Map> entity = restTemplate.getForEntity(dataUrl, Map.class);
        return entity.getBody();

    }

    /**
     * 发布页面
     * @param pageId
     * @return
     */
    public ResponseResult postPage(String pageId) {
        //1 执行页面静态化
        String pageHtml = getPageHtml(pageId);

        //2 查询 CmsPage
        CmsPage cmsPage = findById(pageId).getCmsPage();
        if(cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }

        //3 将页面静态化文件存储到GridFs中
        saveHtml(cmsPage, pageHtml);
        //4 向MQ发消息
        sendPostPage(cmsPage);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Resource
    private RabbitTemplate rabbitTemplate;

    private void sendPostPage(CmsPage cmsPage) {
        //1 生成消息
        // 1.1 准备数据
        Map<String,String> msgMap = new HashMap<>();
        msgMap.put("pageId",cmsPage.getPageId());
        // 1.2 转换json串
        String msg = JSON.toJSONString(msgMap);

        //2 发送
        this.rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, cmsPage.getSiteId() , msg);
    }

    private void saveHtml(CmsPage cmsPage, String pageHtml) {
        try {
            //1 删除静态页面
            String fileId = cmsPage.getHtmlFileId();
            if(StringUtils.isNotBlank(fileId)) {
                gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileId)));
            }

            //2 保存页面到 GridFS
            InputStream inputStream = IOUtils.toInputStream(pageHtml, "UTF-8");
            ObjectId objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());

            //3 更新 CmsPage
            cmsPage.setHtmlFileId(objectId.toHexString());
            cmsPageRepository.save(cmsPage);
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionCast.cast(CommonCode.FAIL);
        }
    }

    public CmsPageResult save(CmsPage cmsPage) {
        CmsPage findCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(findCmsPage !=null){
            //更新
            return this.update(findCmsPage.getPageId(),cmsPage);
        }else{
            //添加
            return this.add(cmsPage);
        }
    }

    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {
        //1. 保存页面
        CmsPageResult save = this.save(cmsPage);
        if(!save.isSuccess()){
            ExceptionCast.cast(CommonCode.FAIL);
        }

        //得到页面的id
        CmsPage cmsPageSave = save.getCmsPage();
        String pageId = cmsPageSave.getPageId();

        //2. 发布页面
        ResponseResult post = this.postPage(pageId);
        if(!post.isSuccess()){
            ExceptionCast.cast(CommonCode.FAIL);
        }

        //3. 得到页面的url
        // 拼接页面Url= cmsSite.siteDomain+cmsSite.siteWebPath+ cmsPage.pageWebPath + cmsPage.pageName
        String siteId = cmsPageSave.getSiteId();
        CmsSite cmsSite = this.findCmsSiteById(siteId);
        //页面url
        //http://www.xuecheng.com/course/detail/402885816243d2dd016243f24c030002.html
        String pageUrl =cmsSite.getSiteDomain() + cmsPageSave.getPageWebPath() + cmsPageSave.getPageName();
        return new CmsPostPageResult(CommonCode.SUCCESS,pageUrl);
    }

    /**
     * 根据站点id查询站点信息
     */
    public CmsSite findCmsSiteById(String siteId){
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
}
