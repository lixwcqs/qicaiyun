package com.cqs.qicaiyun.common.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.Serializable;

/**
 * Created by cqs on 2017/5/29.
 */
public abstract class MongoBaseDaoPagingImpl<T,S extends Serializable>  extends MongoBaseDaoImpl<T,S>{


    public MongoBaseDaoPagingImpl(MongoTemplate template) {
        super(template);
    }
}
