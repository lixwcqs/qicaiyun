package com.cqs.qicaiyun.modules.service;

import com.cqs.configuration.TestBaseServiceConf;
import com.cqs.qicaiyun.mock.TopicMock;
import com.cqs.qicaiyun.modules.entity.Topic;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import javax.annotation.Resource;


/**
 * Created by cqs on 2017/9/13.
 */
@Log4j2
public class TopicServiceTest extends TestBaseServiceConf {

    @Resource(name = "topicServiceImpl")
    private TopicService service;


    @Test
    public void testInsert() throws Exception {
        int count = service.selectCount(null);
        service.insert(TopicMock.newTopic());
    }

    @Test
    public void insertBatch() throws Exception {
        service.insertBatch(TopicMock.newTopics());
    }

    @Test
    public void testSelectById() throws Exception {
        Long id = 907990836791287809L;
        Topic topic = service.selectById(id);
        log.info(topic);
    }
}