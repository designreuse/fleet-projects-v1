package com.comodin.fleet.basic.service;

import com.comodin.fleet.basic.dao.IBaseDao;
import com.github.pagehelper.PageHelper;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Base service.
 *
 * @param <T> the type parameter
 */
public abstract class BaseService<T> implements IBaseService<T> {

    /**
     * The Mapper.
     */
    @Inject
    protected IBaseDao<T> mapper;

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    public T save(T entity) {
        mapper.insert(entity);
        return entity;
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * @param entity
     * @return
     */
    @Override
    public T saveSelective(T entity) {
        mapper.insertSelective(entity);
        return entity;
    }

    @Override
    public int saveList(List<T> recordList) {
        return mapper.insertList(recordList);
    }

    /**
     * 删除方法，依据主键，进行删除
     *
     * @param primaryKey 主键
     */
    @Override
    public void deleteByPrimaryKey(Object primaryKey) {
        mapper.deleteByPrimaryKey(primaryKey);
    }

    /**
     * 批量删除方法，依据主键，进行删除，需要自己在 *Mapper.xml 文件中自己实现.
     *
     * @param primaryKeys 主键
     */
    @Override
    public void batchDeleteByPrimaryKeys(Object... primaryKeys) {
        mapper.batchDeleteByPrimaryKeys(primaryKeys);
    }

    /**
     * 批量业务逻辑上删除，根据 主键，需要自己在 *Mapper.xml 文件中自己实现.
     *
     * @param primaryKeys 主键值
     */
    @Override
    public void batchDeleteFlagByPrimaryKeys(Object... primaryKeys) {
        mapper.batchDeleteFlagByPrimaryKeys(primaryKeys);
    }

    /**
     * 更新方法，依据主键，进行更新全部字段，若字段值为null，也会更新为null
     *
     * @param entity 更新对象【必需要含主键信息】
     */
    @Override
    public void updateAll(T entity) {
        mapper.updateByPrimaryKey(entity);
    }

    /**
     * 更新方法，依据主键，进行更新，字段不为空的数据
     *
     * @param entity 更新对象【必需要含主键信息】
     */
    @Override
    public void updateNotNull(T entity) {
        mapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 查询方法，根据主键，进行查询
     *
     * @param primaryKey 主键
     * @return
     */
    @Override
    public T selectByPrimaryKey(Object primaryKey) {
        return mapper.selectByPrimaryKey(primaryKey);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    /**
     * 查询所有数据，根据 Bean的条件进行查询
     *
     * @param record
     * @return
     */
    @Override
    public List<T> select(T record) {
        return mapper.select(record);
    }

    /**
     * count
     *
     * @param record
     * @return
     */
    @Override
    public int selectCount(T record) {
        return mapper.selectCount(record);
    }
}
