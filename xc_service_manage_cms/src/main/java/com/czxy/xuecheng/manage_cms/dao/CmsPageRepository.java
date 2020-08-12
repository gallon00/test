package com.czxy.xuecheng.manage_cms.dao;

import com.czxy.xuecheng.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liangtong.
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    //根据页面名称、站点Id、页面webpath查询
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}
