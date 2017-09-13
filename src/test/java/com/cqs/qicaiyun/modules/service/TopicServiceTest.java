package com.cqs.qicaiyun.modules.service;

import com.cqs.configuration.BaseConfigurationTestNG;
import com.cqs.qicaiyun.mock.TopicMock;
import com.cqs.qicaiyun.modules.entity.Topic;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;


/**
 * Created by cqs on 2017/9/13.
 */
@Log4j2
public class TopicServiceTest extends BaseConfigurationTestNG {

    @Resource(name = "topicServiceImpl")
    private TopicService service;


    @Test
    public void testInsert() throws Exception {
        int count = service.selectCount(null);
        service.insert(TopicMock.newTopic());
        Assert.assertEquals(count + 1, service.selectCount(null),"写入主题失败");
    }


    @Test
    public void testSelectById() throws Exception {
        Long id = 907990836791287809L;
        Topic topic = service.selectById(id);
        log.info(topic);
    }
}