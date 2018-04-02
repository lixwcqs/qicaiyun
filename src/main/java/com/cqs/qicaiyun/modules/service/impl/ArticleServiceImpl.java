package com.cqs.qicaiyun.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqs.qicaiyun.common.result.PageData;
import com.cqs.qicaiyun.modules.entity.Article;
import com.cqs.qicaiyun.modules.entity.ArticleExample;
import com.cqs.qicaiyun.modules.entity.Content;
import com.cqs.qicaiyun.modules.mapper.ArticleMapper;
import com.cqs.qicaiyun.modules.service.ArticleService;
import com.cqs.qicaiyun.modules.service.ContentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by cqs on 2017/8/20.
 */
@Service
@Log4j2
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource(name = "contentServiceImpl")
    private ContentService contentService;

    @Transactional(propagation = Propagation.NESTED)
    public boolean publish(Article article) {
        Content content = new Content();
        content.setContent(article.getContent());
        boolean insertOK = contentService.insert(content);
        if (!insertOK) {
            log.error("发布文章失败：写入文本内容异常");
            throw new RuntimeException("发布文章失败");
        }
        article.setContentId(content.getId());
        LocalDateTime now = LocalDateTime.now();
        article.setUTime(now);
        article.setUTime(now);
        return insert(article);
    }

    @Override
    public Page<Article> selectPage2(Page<Article> page, Article article) {
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        wrapper.setEntity(article);
        Page<Article> result = selectPage(page, wrapper);
        return result;
    }


    @Override
    public PageData<Article> list(int pageNo, long bmt) {
        int rows = 10;
        ArticleExample example = new ArticleExample();
        PageData<Article> pageData = new PageData<>();
        EntityWrapper<Article> wrapper=new EntityWrapper<>();
        wrapper.setSqlSelect("count(*)");
        pageData.setTotal(selectCount(wrapper));
        example.setOffset((pageNo - 1) * rows + 1);
        example.setLimit(rows);
        final List<Article> articles = baseMapper.selectByExample(example);
        pageData.setPage(pageNo);
        pageData.setList(articles);
        return pageData;
    }



    //将 Page转化为PageData
    private PageData<Article> transferPageToPageData(Page<Article> page) {
        PageData<Article> data = new PageData<>();
        data.setList(page.getRecords());
        data.setTotal(page.getTotal());
        data.setPage(page.getCurrent());
        data.setPages(page.getPages());
        return data;
    }

}
