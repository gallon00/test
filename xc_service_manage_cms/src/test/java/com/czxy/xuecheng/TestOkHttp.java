package com.czxy.xuecheng;

import com.czxy.xuecheng.domain.cms.response.CmsConfigResult;
import com.czxy.xuecheng.manage_cms.ManageCmsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created by liangtong.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManageCmsApplication.class)
public class TestOkHttp {

    @Resource
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplate(){
        ResponseEntity<CmsConfigResult> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", CmsConfigResult.class);
        System.out.println(forEntity.getBody());
    }
}
