package com.myweb.app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.myweb.app.service.Enumable;

import java.util.HashMap;

/**
 * @author nilg
 * @since 2019/9/1 1:04
 */
public enum UserPermissionsTypeEnum implements Enumable {
    /**
     * 超级管理员
     */
    ROOTUSER("超级管理员" ,(Integer)0),
    /**
     * 普通用户
     */
    NORMALUSER("普通用户" ,(Integer)1),
    /**
     * 管理员
     */
    OPERATOR("管理员" ,(Integer)2);

    private String title;
    private Integer value;

    UserPermissionsTypeEnum(String title,Integer value){
        this.title=title;
        this.value=value;
    }

    /**
     * 获取枚举定义的Name
     *
     * @return
     */
    @Override
    public String getName() {
        return this.name();
    }

    /**
     * 获取枚举的描述
     *
     * @return
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * 获取枚举的值
     *
     * @return
     */
    @JsonValue
    @Override
    public Integer getValue() {
        return this.value;
    }

    private static HashMap<Integer, UserPermissionsTypeEnum> map = null;

    private synchronized static void initMap() {
        if (map != null) {
            return;
        }
        map = new HashMap<Integer, UserPermissionsTypeEnum>();
        UserPermissionsTypeEnum[] items = UserPermissionsTypeEnum.values();
        for (UserPermissionsTypeEnum item : items) {
            map.put(item.getValue().intValue(), item);
        }
    }

    @JsonCreator
    public static UserPermissionsTypeEnum find( Integer value) {
        if (value == null) {
            return null;
        }
        if (map == null) {
            initMap();
        }
        return map.get(value);
    }
}
