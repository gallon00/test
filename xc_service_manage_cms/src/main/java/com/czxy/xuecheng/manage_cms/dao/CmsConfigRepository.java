package com.czxy.xuecheng.manage_cms.dao;

import com.czxy.xuecheng.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liangtong.
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
