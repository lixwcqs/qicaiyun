package com.cqs.qicaiyun.modules.controller;

import com.cqs.configuration.TestSimpleController;
import com.cqs.qicaiyun.modules.entity.Article;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by cqs on 2017/9/15.
 */
@Log4j2
public class TopicControllerRestTest extends TestSimpleController {



    @Test
    public void testRecommendTopics() throws Exception {
    }

    @Test
    public void testHotTopics() throws Exception {
        String uri = "http://localhost:9090/qicaiyun/article/list";

        ResponseEntity<List> forEntity = template.getForEntity(uri, List.class);
        List<Article> article = forEntity.getBody();
        System.out.println(article);

    }

    @Test
    public void testHotArticlesTopic() throws Exception {
        //
    }

    @Test
    public void testCommentArticlesTopic() throws Exception {
    }

    @Test
    public void testIncludeArticlesTopic() throws Exception {
    }

    @Test
    public void testFindById() throws Exception {
    }

    @Test
    public void testFollow() throws Exception {
    }

    @Test
    public void testUnfollow() throws Exception {
    }

    @Test
    public void testCreate() throws Exception {
    }

    @Test
    public void testUpdate() throws Exception {
    }

    @Test
    public void testDelete() throws Exception {
    }

}