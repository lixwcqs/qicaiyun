package com.cqs.qicaiyun.modules.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqs.qicaiyun.modules.entity.Article;
import com.cqs.qicaiyun.modules.mapper.ArticleMapper;
import com.cqs.qicaiyun.modules.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * Created by cqs on 2017/8/20.
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
