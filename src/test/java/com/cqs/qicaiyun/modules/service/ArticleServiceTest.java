package com.cqs.qicaiyun.modules.service;

import com.cqs.config.BaseConfigurationTestNG;
import com.cqs.jianshu.modules.entity.Article;
import com.cqs.jianshu.modules.entity.Content;
import com.cqs.mock.ArticleMock;
import com.cqs.mock.ContentMock;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by cqs on 2017/8/20.
 */
@Log4j2
public class ArticleServiceTest extends BaseConfigurationTestNG {

    @Resource(name = "articleServiceImpl")
    private ArticleService service;

    @Resource(name = "contentServiceImpl")
    private ContentService contentService;

    @Test
    public void testInsert() throws Exception {
        Content content = ContentMock.mockContent();
        contentService.insert(content);
        Article article = ArticleMock.mockArticle(content.getId());
        service.insert(article);
    }

    @Test
    public void selectById() throws Exception {
        Article article = service.selectById(899306968995422200L);
        assertNotNull(article);
    }

    @Test
    public void test() throws Exception {
        int count = service.selectCount(null);
        List<Article> list = service.selectList(null);
        assertEquals(count,list.size());
        list.stream().forEach(System.out::println);

    }
}