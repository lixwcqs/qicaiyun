package com.cqs.qicaiyun.modules.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cqs.configuration.TestBaseServiceConf;
import com.cqs.qicaiyun.common.result.PageData;
import com.cqs.qicaiyun.mock.ArticleMock;
import com.cqs.qicaiyun.modules.entity.Article;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by cqs on 2017/8/20.
 */
@Log4j2
public class ArticleServiceTest extends TestBaseServiceConf {

    @Resource(name = "articleServiceImpl")
    private ArticleService service;

    @Test
    public void testInsert() throws Exception {
        for (int i = 0; i < 1; i++) {
            Article article = ArticleMock.mockArticle();
            System.out.println(article);
        }

    }

    @Test
    public void selectById() throws Exception {
        Article article = service.selectById(54L);
        assertNotNull(article);
        System.out.println(article);
    }

    @Test
    public void test() throws Exception {
        int count = service.selectCount(null);
        List<Article> list = service.selectList(null);
        assertEquals(count,list.size());
        list.stream().forEach(System.out::println);
    }


    @Test
    public void testDeleteById() throws Exception {
        final boolean b = service.deleteById(1000);
        System.out.println(b);
    }

    @Test
    public void testSelectPage() throws Exception {
        Page<Article> page =new Page<>();
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
//        wrapper.;
        Page<Article> articlePage = service.selectPage(page, wrapper);
        System.out.println(articlePage);
    }


    @Test
    public void list() throws Exception {
        final PageData<Article> list = service.list(1, 10);
        System.out.println(list);
    }
}