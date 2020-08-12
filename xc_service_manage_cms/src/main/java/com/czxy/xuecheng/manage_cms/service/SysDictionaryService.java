package com.czxy.xuecheng.manage_cms.service;

import com.czxy.xuecheng.common.model.response.CommonCode;
import com.czxy.xuecheng.common.model.response.ResultCode;
import com.czxy.xuecheng.domain.system.SysDictionary;
import com.czxy.xuecheng.domain.system.response.SysDictionaryResult;
import com.czxy.xuecheng.manage_cms.dao.SysDictionaryRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by liangtong.
 */
@Service
public class SysDictionaryService {
    @Resource
    private SysDictionaryRepository sysDictionaryRepository;

    public SysDictionaryResult findByType(String dType){
        SysDictionary sysDictionary = sysDictionaryRepository.findByDType(dType);
        return new SysDictionaryResult(CommonCode.SUCCESS, sysDictionary );
    }
}
