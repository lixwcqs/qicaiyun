package com.cqs.qicaiyun.modules.service;

import com.cqs.qicaiyun.modules.entity.ArticleMongo;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

/**
 * @author lixiaowen
 * @create 18-12-1
 */
public interface ArticleMongoService {

    /**
     *  新增文章内容
     * @param articleMongo
     */
    ArticleMongo save(ArticleMongo articleMongo);


    /**
     * 更新文章内容
     * @param articleMongo
     */
    UpdateResult update(ArticleMongo articleMongo);


    /**
     * 根据Mongo主键查找
     * @param id
     * @return
     */
    ArticleMongo findById(String id);


    /**
     * 根据主键删除
     * @param id
     * @return
     */
    DeleteResult deleteById(String id);
}
