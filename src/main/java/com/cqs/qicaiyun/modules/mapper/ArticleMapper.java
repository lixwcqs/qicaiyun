package com.cqs.qicaiyun.modules.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cqs.qicaiyun.modules.entity.Article;
import com.cqs.qicaiyun.modules.entity.ArticleExample;

import java.util.List;


/**
 * Created by cqs on 2017/8/20.
 */
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> selectByExample(ArticleExample example);
}
