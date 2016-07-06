package com.comodin.fleet.basic.service;

import java.util.List;

/**
 * 通用接口
 *
 * @param <T> the type parameter
 */
public interface IBaseService<T> {

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param entity the entity
     * @return t
     */
    T save(T entity);


    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity the entity
     * @return t
     */
    T saveSelective(T entity);

    /**
     * Save list int.
     *
     * @param recordList the record list
     * @return the int
     */
    int saveList(List<T> recordList);

    /**
     * 删除方法，依据主键，进行删除
     *
     * @param primaryKey 主键
     */
    void deleteByPrimaryKey(Object primaryKey);

    /**
     * 批量业务逻辑上删除，根据 主键，需要自己在 *Mapper.xml 文件中自己实现.
     *
     * @param primaryKeys 主键值
     */
    void batchDeleteFlagByPrimaryKeys(Object... primaryKeys);

    /**
     * 批量删除方法，依据主键，进行删除，需要自己在 *Mapper.xml 文件中自己实现.
     *
     * @param primaryKeys 主键
     */
    void batchDeleteByPrimaryKeys(Object... primaryKeys);

    /**
     * 更新方法，依据主键，进行更新全部字段，若字段值为null，也会更新为null
     *
     * @param entity 更新对象【必需要含主键信息】
     */
    void updateAll(T entity);

    /**
     * 更新方法，依据主键，进行更新，字段不为空的数据
     *
     * @param entity 更新对象【必需要含主键信息】
     */
    void updateNotNull(T entity);

    /**
     * 查询方法，根据主键，进行查询
     *
     * @param primaryKey 主键
     * @return t
     */
    T selectByPrimaryKey(Object primaryKey);

    /**
     * 查询所有数据
     *
     * @return list
     */
    List<T> selectAll();


    /**
     * 查询所有数据，根据 Bean的条件进行查询
     *
     * @param record the record
     * @return list
     */
    List<T> select(T record);

    /**
     * count
     *
     * @param record the record
     * @return int
     */
    int selectCount(T record);

}
