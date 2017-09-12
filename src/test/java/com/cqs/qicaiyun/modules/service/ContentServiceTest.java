package com.cqs.qicaiyun.modules.service;

import com.cqs.configuration.BaseConfigurationTestNG;
import com.cqs.qicaiyun.mock.ContentMock;
import com.cqs.qicaiyun.modules.entity.Content;
import org.testng.annotations.Test;

import javax.annotation.Resource;

import static org.testng.Assert.assertNotNull;

/**
 * Created by cqs on 2017/8/20.
 */
public class ContentServiceTest extends BaseConfigurationTestNG {

    @Resource(name = "contentServiceImpl")
    private ContentService service;

    @Test
    public void testSelectById() throws Exception {
        assertNotNull(service);
        Content content = service.selectById("1");
        System.out.println(content);
    }

    @Test
    public void testInsert() throws Exception {
        Content content = ContentMock.mockContent();
        service.insert(content);
        System.out.println(content);
    }
}