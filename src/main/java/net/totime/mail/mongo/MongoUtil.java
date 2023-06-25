/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package net.totime.mail.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.ObjectUtils;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/12
 * @description Mongo工具类
 * @since 1.0.0
 */
@Component
public class MongoUtil {
    @Resource
    private MongoTemplate mt;

    /**
     * 插入文档
     *
     * @param collectionName 集合名称
     * @param obj            对象
     * @return boolean
     */
    public boolean insertDocument(String collectionName, Object obj) {
        Object insert = mt.insert(obj, collectionName);
        return !ObjectUtils.isEmpty(insert);
    }

    /**
     * 插入文档
     *
     * @param obj 对象
     * @return boolean
     */
    public boolean insertDocument(Object obj) {
        Object insert = mt.insert(obj);
        return !ObjectUtils.isEmpty(insert);
    }

    /**
     * 更新文档
     *
     * @param collectionName 集合名称
     * @param obj            对象
     * @return boolean
     */
    public boolean updateDocument(String collectionName, Object obj) {
        Object save = mt.save(obj, collectionName);
        return !ObjectUtils.isEmpty(save);
    }

    /**
     * 更新文档
     *
     * @param obj 对象
     * @return boolean
     */
    public boolean updateDocument(String collectionName, Object obj, Query query) {
        Document document = new Document();
        mt.getConverter().write(obj, document);
        UpdateResult updateResult = mt.getCollection(collectionName).replaceOne(query.getQueryObject(), document);
        return updateResult.getModifiedCount() > 0;
    }

    /**
     * 删除文档
     *
     * @param collectionName 集合名称
     * @param obj            对象
     * @return boolean
     */
    public boolean deleteDocument(String collectionName, Object obj) {
        DeleteResult remove = mt.remove(obj, collectionName);
        return remove.getDeletedCount() > 0;
    }

    /**
     * 删除文档
     *
     * @param obj 对象
     * @return boolean
     */
    public boolean deleteDocument(Object obj) {
        DeleteResult remove = mt.remove(obj);
        return remove.getDeletedCount() > 0;
    }

    /**
     * 条件查询文档
     *
     * @param collectionName 集合名称
     * @param obj            对象
     * @param query          查询条件对象
     * @return boolean 是否存在
     */
    public Object findDocument(String collectionName, Object obj, Query query) {
        return mt.findOne(query, obj.getClass(), collectionName);
    }

    /**
     * 条件查询文档
     *
     * @param obj   对象
     * @param query 查询条件对象
     * @return boolean 是否存在
     */
    public Object findDocument(Object obj, Query query) {
        return mt.findOne(query, obj.getClass());
    }

    /**
     * 条件查询文档，使用泛型返回对应对象
     *
     * @param collectionName 集合名称
     * @param clazz          对象
     * @param query          查询条件对象
     * @return {@link T} 对象
     */
    public <T> T findDocument(String collectionName, Class<T> clazz, Query query) {
        return mt.findOne(query, clazz, collectionName);
    }

    /**
     * 条件查询文档，使用泛型返回对应对象
     *
     * @param clazz 对象
     * @param query 查询条件对象
     * @return {@link T} 对象
     */
    public <T> T findDocument(Class<T> clazz, Query query) {
        return mt.findOne(query, clazz);
    }

    /**
     * 条件删除文档
     *
     * @param collectionName 集合名称
     * @param obj            对象
     * @param query          查询条件对象
     * @return boolean 是否存在
     */
    public boolean deleteDocument(String collectionName, Object obj, Query query) {
        DeleteResult remove = mt.remove(query, obj.getClass(), collectionName);
        return remove.getDeletedCount() > 0;
    }

    /**
     * 条件删除文档
     *
     * @param collectionName 集合名称
     * @param query          查询条件对象
     * @return boolean 是否存在
     */
    public boolean deleteDocument(String collectionName, Query query) {
        DeleteResult remove = mt.remove(query, collectionName);
        return remove.getDeletedCount() > 0;
    }

    /**
     * 条件删除文档
     *
     * @param obj   对象
     * @param query 查询条件对象
     * @return boolean 是否存在
     */
    public boolean deleteDocument(Object obj, Query query) {
        DeleteResult remove = mt.remove(query, obj.getClass());
        return remove.getDeletedCount() > 0;
    }

    /**
     * 条件删除文档
     *
     * @param clazz 对象
     * @param query 查询条件对象
     * @return boolean 是否存在
     */
    public boolean deleteDocument(Class clazz, Query query) {
        DeleteResult remove = mt.remove(query, clazz);
        return remove.getDeletedCount() > 0;
    }

    /**
     * 条件更新文档
     *
     * @param collectionName 集合名称
     * @param up             更新
     * @param query          查询条件对象
     * @return boolean 是否存在
     */
    public boolean updateDocument(String collectionName, UpdateDefinition up, Query query) {
        Object update = mt.updateFirst(query, up, collectionName);
        return !ObjectUtils.isEmpty(update);
    }

    /**
     * 条件更新文档
     *
     * @param up    更新
     * @param query 查询条件对象
     * @return boolean 是否存在
     */
    public <T> boolean updateDocument(UpdateDefinition up, Query query, Class<T> clazz) {
        Object update = mt.updateFirst(query, up, clazz);
        return !ObjectUtils.isEmpty(update);
    }

    /**
     * 分页查询
     *
     * @param collectionName 集合名称
     * @param obj            对象
     * @param query          查询条件对象
     * @param page           页码
     * @param size           每页数量
     * @return boolean 是否存在
     */
    public Object findDocument(String collectionName, Object obj, Query query, int page, int size) {
        return mt.find(query.skip((long) (page - 1) * size).limit(size), obj.getClass(), collectionName);
    }

    /**
     * 分页查询
     *
     * @param obj   对象
     * @param query 查询条件对象
     * @param page  页码
     * @param size  每页数量
     * @return boolean 是否存在
     */
    public Object findDocument(Object obj, Query query, int page, int size) {
        return mt.find(query.skip((long) (page - 1) * size).limit(size), obj.getClass());
    }

    /**
     * 分页查询
     *
     * @param clazz 对象
     * @param query 查询条件对象
     * @param page  页码
     * @param size  每页数量
     * @return {@link List}<{@link T}> 对象
     */
    public <T> List<T> findDocument(Class<T> clazz, Query query, int page, int size) {
        return mt.find(query.skip((long) (page - 1) * size).limit(size), clazz);
    }
}
