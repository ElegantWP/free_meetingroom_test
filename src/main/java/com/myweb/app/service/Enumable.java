package com.myweb.app.service;

import java.io.Serializable;

/**
 * @author nilg
 * @since 2019/9/1 1:06
 */
public interface Enumable extends Serializable {

    /**
     * 获取枚举定义的Name
     * @return
     */
    String getName();

    /**
     * 获取枚举的描述
     * @return
     */
    String getTitle();

    /**
     * 获取枚举的值
     * @return
     */
    Integer getValue();

    /**
     * 此枚举是否是业务枚举，是否隐藏
     * @return
     */
}
