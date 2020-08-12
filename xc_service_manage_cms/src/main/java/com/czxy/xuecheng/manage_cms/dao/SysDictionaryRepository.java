package com.czxy.xuecheng.manage_cms.dao;

import com.czxy.xuecheng.domain.cms.CmsTemplate;
import com.czxy.xuecheng.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liangtong.
 */
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
    /**
     * 通过类型查询
     * @param dType
     * @return
     */
    public SysDictionary findByDType(String dType);
}
