package com.myweb.app.core;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author weipan
 * @date 2019/8/20 21:26
 * @description  定制版MyBatis Mapper插件接口
 */
public interface Mapper<T>
    extends
    BaseMapper<T>,
    ConditionMapper<T>,
    IdsMapper<T>,
    InsertListMapper<T> {
}
