package com.myweb.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myweb.app.entity.UserPermissions;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author nilg
 * @since 2019/8/31 22:23
 */
@Mapper
@Repository
public interface UserPermissionsMapper extends BaseMapper<UserPermissions> {
}
