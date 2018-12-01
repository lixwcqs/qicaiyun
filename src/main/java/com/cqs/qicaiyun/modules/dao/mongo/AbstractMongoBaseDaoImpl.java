package com.cqs.qicaiyun.modules.dao.mongo;

import com.mongodb.client.result.DeleteResult;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

/**
 * spring-data-mongodb API文档说明感觉一般
 * <p>
 * 可以结合 https://github.com/spring-projects/spring-data-mongodb/tree/master/spring-data-mongodb/src/test/java/org/springframework/data/mongodb
 * 学习怎么用
 *
 * 例如MongoTemplate见https://github.com/spring-projects/spring-data-mongodb/blob/master/spring-data-mongodb/src/test/java/org/springframework/data/mongodb/core/MongoTemplateTests.java
 * <p>
 * Created by cqs on 2017/5/29.
 */
public abstract class AbstractMongoBaseDaoImpl<T, D extends Serializable> implements MongoBaseDao<T, D> {


    protected abstract MongoTemplate getTemplate();

    /***
     * 求解实体类的类类型
     *
     * @return
     */
    protected Class<T> getEntityClass() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked")
        Class<T> entityClass = (Class<T>) type.getActualTypeArguments()[0];
        return entityClass;
    }

    @Override
    public T insert(T entity) {
        return getTemplate().insert(entity);
    }

    @Override
    public Collection<T> batchInsert(Collection<T> entities) {
        return getTemplate().insertAll(entities);
    }

    @Override
    public DeleteResult deleteAll() {
        return getTemplate().remove(new Query(), getEntityClass());
    }


    @Override
    public List<T> findByExample(T entity) {
        Criteria criteria = Criteria.byExample(Example.of(entity));
        Query query = new Query(criteria);
        return getTemplate().find(query, getEntityClass());
    }

    @Override
    public T findById(D id) {
        return getTemplate().findById(id, getEntityClass());
    }

    @Override
    public boolean exists(D id) {
        return findById(id) == null;
    }

    @Override
    public List<T> findAll() {

        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"createDate")));
        return getTemplate().find(query,getEntityClass());
    }


    @Override
    public long count() {
        return getTemplate().count(null, getEntityClass());
    }

    @Override
    public DeleteResult deleteById(D id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return getTemplate().remove(query, getEntityClass());
    }

    @Override
    public DeleteResult delete(T entity) {
        return getTemplate().remove(entity);
    }
}
