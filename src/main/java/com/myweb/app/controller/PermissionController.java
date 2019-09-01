package com.myweb.app.controller;

import com.myweb.app.core.Result;
import com.myweb.app.core.ServiceException;
import com.myweb.app.dao.UserPermissionsMapper;
import com.myweb.app.entity.UserPermissions;
import com.myweb.app.model.UserContent;
import com.myweb.app.service.UserPermissionsService;
import com.myweb.app.service.YonZoneService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.List;


@Api(value = "权限相关操作")
@RestController
@RequestMapping("/rest/web/permission")
public class PermissionController {
    @Autowired
    YonZoneService yonZoneService;
    @Autowired
    UserPermissionsService userPermissionsService;

    /**
     * 添加管理员
     */

    /**
     * 删除管理员
     */

    /**
     * 保存权限
     * @param userId
     * @param type
     */
    @RequestMapping(value = "/save/permissions",method = RequestMethod.POST)
    public Result saveDefaultPermissions(Long userId, Integer type) {
        return userPermissionsService.saveUserPermissions(userId,type);
    }

    /**
     * 登录获取权限
     */

    @RequestMapping(value = "/get/permissions",method = RequestMethod.GET)
    public UserPermissions getUserPermissions() {
        UserContent userContent = yonZoneService.getUserContent(yonZoneService.getAccessToken());
        if(null == userContent){
            throw new ServiceException("未获取到该登录用户信息!");
        }
        Long userId = Long.parseLong(userContent.getUserId());
        if(null == userId){
            throw new ServiceException("当前登录用户信息不全，无法权限操作!");
        }
        //首次登录权限表没有改用户数据
        userPermissionsService.saveUserPermissions(userId,null);
        UserPermissions userPermissions = new UserPermissions();
        userPermissions = userPermissionsService.getUserPermissions(userId);
        return  userPermissions;
    }

}
