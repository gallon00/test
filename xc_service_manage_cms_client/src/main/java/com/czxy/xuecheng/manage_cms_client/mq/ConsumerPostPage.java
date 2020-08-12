package com.czxy.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.czxy.xuecheng.domain.cms.CmsPage;
import com.czxy.xuecheng.manage_cms_client.service.PageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/** 监听MQ，接收页面发布消息
 * Created by liangtong.
 */
@Component
public class ConsumerPostPage {
    @Resource
    private PageService pageService;

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg){
        //解析消息
        Map map = JSON.parseObject(msg, Map.class);
        //得到消息中的页面id
        String pageId = (String) map.get("pageId");

        //调用service方法将页面从GridFs中下载到服务器
        pageService.savePageToServerPath(pageId);

    }
}
