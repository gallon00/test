package com.czxy.xuecheng.manage_cms.dao;

import com.czxy.xuecheng.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liangtong.
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {
    /**
     * 通过模板名称查询
     * @param templateName
     * @return
     */
    public CmsTemplate findByTemplateName(String templateName);
}
