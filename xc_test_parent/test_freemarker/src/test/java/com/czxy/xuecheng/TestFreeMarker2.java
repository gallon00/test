package com.czxy.xuecheng;

import com.czxy.xuecheng.domain.cms.CmsConfig;
import com.czxy.xuecheng.domain.cms.CmsConfigModel;
import com.czxy.xuecheng.domain.cms.response.CmsConfigResult;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liangtong.
 */
public class TestFreeMarker2 {
    @Test
    public void testGenerateHtml() throws Exception {
        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());

        // 设置模板路径
        String classpath = this.getClass().getResource("/").getPath();
        File templatesDir = new File(classpath, "/templates/");
        configuration.setDirectoryForTemplateLoading(templatesDir);

        //设置字符集
        configuration.setDefaultEncoding("UTF-8");

        //加载模板
        Template template = configuration.getTemplate("index_banner.ftl");

        //模型数据
//        CmsConfigResult cmsConfigResult = new CmsConfigResult();
//        CmsConfig cmsConfig = new CmsConfig();
//        List<CmsConfigModel> model = new ArrayList<>();
//        CmsConfigModel cmsConfigModel = new CmsConfigModel();
//        cmsConfigModel.setValue("xxxx");
//        model.add(cmsConfigModel);
//        cmsConfig.setModel(model);
//        cmsConfigResult.setCmsConfig(cmsConfig);
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        String dataUrl = "http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f";
        ResponseEntity<CmsConfigResult> entity = restTemplate.getForEntity(dataUrl, CmsConfigResult.class);
        CmsConfigResult cmsConfigResult = entity.getBody();

        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, cmsConfigResult);

        //输出
        File file = new File(templatesDir , "test.html");
        System.out.println(file.getAbsolutePath());
        FileUtils.writeStringToFile( file ,content);
    }

}
