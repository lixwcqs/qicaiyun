package com.cqs.qicaiyun.common.mongo;

import com.mongodb.WriteResult;
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
public abstract class MongoBaseDaoImpl<T, D extends Serializable> implements MongoBaseDao<T, D> {

    private MongoTemplate template;

    public MongoBaseDaoImpl(MongoTemplate template) {
        this.template = template;
    }

    protected MongoTemplate getTemplate() {
        return this.template;
    }

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

    //--------------------------------增----------------------------------------------------
    @Override
    public void insert(T entity) {
        getTemplate().insert(entity);
    }

    //
    @Override
    public void batchInsert(Collection<T> entities) {
        getTemplate().insertAll(entities);
    }

    //--------------------------------删----------------------------------------------------
    @Override
    public WriteResult deleteAll() {
        return getTemplate().remove(new Query(), getEntityClass());
    }


    //--------------------------------改----------------------------------------------------
    //为了不丢失属性 觉得 具体的修改 需要在具体的业务层做
    public void update(T entity) {
        getTemplate().save(entity);
    }


    //--------------------------------查----------------------------------------------------
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
    public WriteResult deleteById(D id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));//
        return getTemplate().remove(query, getEntityClass());
    }

    @Override
    public WriteResult delete(T entity) {
        return getTemplate().remove(entity);
    }


    //查询和更新
}
