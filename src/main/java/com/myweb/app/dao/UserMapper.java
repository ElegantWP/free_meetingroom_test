package com.myweb.app.dao;

import com.myweb.app.core.Mapper;
import com.myweb.app.entity.User;
import org.springframework.stereotype.Repository;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {
}