package com.comodin.fleet.basic.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by supeng on 2016-04-23 0023.
 *
 * @param <T> the type parameter
 */
public interface IBaseDao<T> extends Mapper<T>, MySqlMapper<T> {

    /**
     * 批量删除，根据 主键，需要自己在 *Mapper.xml 文件中自己实现.
     *
     * @param primaryKeys 主键值
     */
    void batchDeleteByPrimaryKeys(Object... primaryKeys);

    /**
     * 批量业务逻辑上删除，根据 主键，需要自己在 *Mapper.xml 文件中自己实现.
     *
     * @param primaryKeys 主键值
     */
    void batchDeleteFlagByPrimaryKeys(Object... primaryKeys);
}
