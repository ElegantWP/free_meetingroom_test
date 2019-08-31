package com.myweb.app.controller;

import com.myweb.app.core.ServiceException;
import com.myweb.app.model.UserContent;
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

    /**
     * 添加管理员
     */

    /**
     * 删除管理员
     */

    /**
     * 增加权限
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public void saveDefaultPermissions() {
        List<UserContent> userContents = yonZoneService.getUserContentList(yonZoneService.getAccessToken());
        if(CollectionUtils.isEmpty(userContents)){
            throw  new ServiceException("未获取到该登录用户信息!");
        }



    }

    /**
     * 修改权限
     */


    /**
     * 删除权限
     */
}
