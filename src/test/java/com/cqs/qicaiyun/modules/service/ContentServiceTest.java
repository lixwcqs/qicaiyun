package com.cqs.qicaiyun.modules.service;

import com.cqs.config.BaseConfigurationTestNG;
import com.cqs.jianshu.modules.entity.Content;
import com.cqs.mock.ContentMock;
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