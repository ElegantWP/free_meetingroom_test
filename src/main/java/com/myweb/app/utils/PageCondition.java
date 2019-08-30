package com.myweb.app.utils;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author  wanggqf
 * @desc     分页，条件查询的 相关对象
 */
@Data
public class PageCondition {
    private Integer currentPage;   //当前页数
    private Integer size = 5;//每页个数 默认是五
    private String name;  //会议室名称
    private String capacity; // 会议室容量
    private Integer[] states;   //使用状态的数组。
    private Integer  active;   //激活状态
}
