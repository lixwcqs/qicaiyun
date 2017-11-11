package com.cqs.qicaiyun.mock;

import com.cqs.qicaiyun.modules.entity.Article;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created by cqs on 2017/8/21.
 */
@Log
public class ArticleMock {

    private static Random random = new Random();

    public static Article mockArticle(){
        Article article = new Article();
        article.setAuthor("七蒙主");
        article.setContent("第三次财富大洗牌第三次财富大洗牌第三次财富大洗牌");
        article.setTitle("第三次财富大洗牌，如何成为真正的赢家");
        article.setCTime(LocalDateTime.now());
//        article.setUTime(new Date());
        article.setReading(random.nextInt(100000));
        article.setUp(random.nextInt(100));
        return article;
    }
}
