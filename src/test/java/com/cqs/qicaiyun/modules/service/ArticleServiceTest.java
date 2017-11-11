package com.cqs.qicaiyun.modules.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cqs.configuration.BaseConfigurationTestNG;
import com.cqs.qicaiyun.mock.ArticleMock;
import com.cqs.qicaiyun.modules.entity.Article;
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
        for (int i = 0; i < 1; i++) {
            Article article = ArticleMock.mockArticle();
            service.publish(article);
            System.out.println(article);
        }

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


    @Test
    public void testSelectByExample() throws Exception {
        Page<Article> page =new Page<>();
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
//        wrapper.;
        Page<Article> articlePage = service.selectPage(page, wrapper);
        System.out.println(articlePage);
    }
}