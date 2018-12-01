package com.cqs.qicaiyun.modules.dao;

import com.cqs.qicaiyun.modules.dao.mongo.AbstractMongoBaseDaoImpl;
import com.cqs.qicaiyun.modules.entity.ArticleMongo;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.log4j.Log4j2;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lixiaowen
 * @create 18-12-1
 */
@Log4j2
@Repository
public class ArticleMongoDao extends AbstractMongoBaseDaoImpl<ArticleMongo,String> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    protected MongoTemplate getTemplate() {
        return mongoTemplate;
    }

    public UpdateResult update(ArticleMongo articleMongo) {
        Query query = new Query();
        Asserts.notEmpty(articleMongo.getId(),"文章主键");
        query.addCriteria(Criteria.where("id").is(articleMongo.getId()));
        Update update = new Update();
        update.set("content",articleMongo.getContent());
        return mongoTemplate.updateFirst(query,update,ArticleMongo.class);
    }
}
