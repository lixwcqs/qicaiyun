package com.cqs.qicaiyun.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cqs.qicaiyun.common.result.PageData;
import com.cqs.qicaiyun.modules.entity.Article;


/**
 * Created by cqs on 2017/8/20.
 */
public interface ArticleService extends IService<Article> {


    boolean publish(Article article);
    @Deprecated
    Page<Article> selectPage2(Page<Article> page, Article article);

    PageData<Article> list(int pageNo, long bmt);
}
