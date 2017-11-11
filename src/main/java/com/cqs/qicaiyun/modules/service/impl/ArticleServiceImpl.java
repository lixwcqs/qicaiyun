package com.cqs.qicaiyun.modules.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqs.qicaiyun.modules.entity.Article;
import com.cqs.qicaiyun.modules.entity.Content;
import com.cqs.qicaiyun.modules.mapper.ArticleMapper;
import com.cqs.qicaiyun.modules.service.ArticleService;
import com.cqs.qicaiyun.modules.service.ContentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by cqs on 2017/8/20.
 */
@Service
@Log4j2
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource(name = "contentServiceImpl")
    private ContentService contentService;


    @Transactional(propagation = Propagation.NESTED)
    public boolean publish(Article article){
        Content content = new Content();
        content.setContent(article.getContent());
        for (int i = 0; i < 3; i++) {
            //这里执行过程没有一行 且有事务控制
            boolean insertOK = contentService.insert(content);
            if (!insertOK) {
                log.error("发布文章失败：写入文本内容异常");
                throw new RuntimeException("发布文章失败");
            }
        }

        article.setContentId(content.getId());
        LocalDateTime now = LocalDateTime.now();
        article.setUTime(now);
        article.setUTime(now);
        //抛出异常 导致publish()方法上的事务提交失败
        if(true) throw new RuntimeException();
        return insert(article);
    }


    @Override
    public Article selectById(Serializable id) {
        Article article = selectById(id);
        //查询出内容
        Content content = contentService.selectById(article.getId());
        article.setContent(content.getContent());
        return article;
    }
}
