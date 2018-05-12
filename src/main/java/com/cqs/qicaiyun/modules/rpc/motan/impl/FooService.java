package com.cqs.qicaiyun.modules.rpc.motan.impl;

import com.cqs.qicaiyun.modules.entity.Article;
import com.cqs.qicaiyun.modules.rpc.motan.IFooService;
import com.cqs.qicaiyun.modules.service.ArticleService;

import javax.annotation.Resource;

/**
 * Created by cqs on 2018/4/2.
 */
//@MotanService
public class FooService implements IFooService {

    @Resource(name = "articleServiceImpl")
    ArticleService articleService;

    @Override
    public Article hello(int id) {
        return articleService.selectById(id);
    }
}
