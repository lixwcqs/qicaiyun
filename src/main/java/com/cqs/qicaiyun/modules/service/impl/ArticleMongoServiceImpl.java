package com.cqs.qicaiyun.modules.service.impl;

import com.cqs.qicaiyun.modules.dao.ArticleMongoDao;
import com.cqs.qicaiyun.modules.entity.ArticleMongo;
import com.cqs.qicaiyun.modules.service.ArticleMongoService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 文章实现类
 * @author lixiaowen
 * @create 18-12-1
 */
@Service
public class ArticleMongoServiceImpl implements ArticleMongoService {

    private final static String COLLECTION_NAME = "article";

    @Autowired
    private  ArticleMongoDao articleDao;


    /**
     * 新增
     * @param articleMongo
     * @return
     */
    @Override
    public ArticleMongo save(ArticleMongo articleMongo) {
       return articleDao.insert(articleMongo);
    }

    /**
     * 更新
     * @param articleMongo
     */
    @Override
    public UpdateResult update(ArticleMongo articleMongo) {
        return articleDao.update(articleMongo);
    }

    @Override
    public ArticleMongo findById(String id) {
        return articleDao.findById(id);
    }

    /**
     * 根据mongo删除
     * @param id
     * @return
     */
    @Override
    public DeleteResult deleteById(String id) {
        return articleDao.deleteById(id);
    }

}
