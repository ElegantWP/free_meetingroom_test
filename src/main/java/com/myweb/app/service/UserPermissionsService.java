package com.myweb.app.service;

import com.myweb.app.core.Result;
import com.myweb.app.entity.UserPermissions;

/**
 * @author nilg
 * @since 2019/8/31 12:23
 */
public interface UserPermissionsService {

    UserPermissions getUserPermissions(Long userId);

    Result saveUserPermissions(Long userId,Integer type);
}
