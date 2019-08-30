package com.myweb.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myweb.app.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author weipan
 * @date 2019/8/22 15:17
 * @description
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

  <T> void selectList(Page<T> tPage, Object o);
}
