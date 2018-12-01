package com.cqs.qicaiyun.modules.service;

import com.cqs.configuration.TestBaseServiceConf;
import com.cqs.qicaiyun.modules.entity.ArticleMongo;
import com.mongodb.client.result.DeleteResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class ArticleMongoServiceTest extends TestBaseServiceConf {

    @Autowired
    private ArticleMongoService service;


    /**
     *
     */
    @Test
    public void save() {
        ArticleMongo articleMongo = new ArticleMongo();
        articleMongo.setContent("hello world 生情的过程中碰撞出的火花也足够灼伤别人了，它并不像王小波形容的爱情一样：“我和你就好像两个小孩子，围着一个秘密的果酱罐，一点一点地尝它，看看里面有多少甜”。我们的果酱一定不是甜蜜的草莓味或者是温柔的橙子味，我们的果酱是呛得人流眼泪的芥末味是让人爱憎分明的榴莲味。我们这样别扭固执的两个人如果没有的调味的芥末，这份爱情一定腥臭的让人难以下咽，我们这样的爱情如同。");
        service.save(articleMongo);
    }

    @Test
    public void findById() {
        ArticleMongo articleMongo = service.findById("5c0270c88da1827e41c50dc7");
        System.out.println(articleMongo);
    }

    @Test
    public void updateContent() {
        ArticleMongo articleMongo = new ArticleMongo();
        articleMongo.setId("5c029952fcf4251c8495d011");
        articleMongo.setContent("new Content" + LocalDateTime.now().toString());
        service.update(articleMongo);
    }

    @Test
    public void deleteById() {
        DeleteResult deleteResult = service.deleteById("5c029c19fcf4251f9e5ed25b");
        System.out.println(deleteResult);
    }
}