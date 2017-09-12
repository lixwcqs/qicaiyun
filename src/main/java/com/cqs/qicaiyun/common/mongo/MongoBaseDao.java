package com.cqs.qicaiyun.common.mongo;

import com.mongodb.WriteResult;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by cqs on 2017/5/29.
 */
public interface MongoBaseDao<T, S extends Serializable>  {

    void insert(T entity);

    /**
     * Saves all given entities.
     *
     * @param entities
     * @return the saved entities
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    void batchInsert(Collection<T> entities);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    T findById(S id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    boolean exists(S id);


    List<T> findByExample(T entity);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    List<T> findAll();


    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    WriteResult deleteById(S id);

    /**
     * Deletes a given entity.
     *
     * @param entity
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    WriteResult delete(T entity);


    /**
     * Deletes all entities managed by the repository.
     */
    WriteResult deleteAll();
}
