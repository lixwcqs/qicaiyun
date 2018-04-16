package com.cqs.qicaiyun.modules.rpc.motan;

import com.cqs.qicaiyun.modules.entity.Article;

/**
 * Created by cqs on 2018/4/2.
 */
public interface IFooService {
    Article hello(int id);
}