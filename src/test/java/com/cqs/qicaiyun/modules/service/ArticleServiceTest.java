package com.cqs.qicaiyun.modules.service;

import com.cqs.configuration.BaseConfigurationTestNG;
import com.cqs.qicaiyun.mock.ArticleMock;
import com.cqs.qicaiyun.mock.ContentMock;
import com.cqs.qicaiyun.modules.entity.Article;
import com.cqs.qicaiyun.modules.entity.Content;
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
        System.out.println(article);
    }

    @Test
    public void selectById() throws Exception {
        Article article = service.selectById(1);
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