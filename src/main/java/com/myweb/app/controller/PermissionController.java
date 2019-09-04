package com.myweb.app.controller;

import com.alibaba.fastjson.JSON;
import com.myweb.app.core.Result;
import com.myweb.app.core.ResultGenerator;
import com.myweb.app.core.ServiceException;
import com.myweb.app.dao.UserPermissionsMapper;
import com.myweb.app.dto.YouZoneUser;
import com.myweb.app.entity.UserPermissions;
import com.myweb.app.model.DiworkUsers;
import com.myweb.app.model.UserContent;
import com.myweb.app.model.YonZoneMsgModel;
import com.myweb.app.service.UserPermissionsService;
import com.myweb.app.service.YonZoneService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Api(value = "权限相关操作")
@RestController
@RequestMapping("/rest/web/permission")
public class PermissionController {
    Logger logger = LoggerFactory.getLogger(PermissionController.class);

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
    public Result saveDefaultPermissions(@RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "type",required = false) Integer type) {
        return userPermissionsService.saveUserPermissions(userId,type);
    }


    /**
     * 登录获取权限
     * @return
     */
    @RequestMapping(value = "/get/permissions",method = RequestMethod.POST)
    public UserPermissions getUserPermissions(@RequestParam(value = "code",required = false) String code) {
        String accessToken = yonZoneService.getAccessToken();
        if(accessToken.equals(null)){
            throw new ServiceException("未能获取到授权令牌!");
        }
        logger.info("accessToken data:{}",JSON.toJSONString(accessToken));
        System.out.println("accessToken:"+accessToken+";code:"+code);
        YouZoneUser userContent = yonZoneService.getUserContent(code,accessToken);
        if(null == userContent){
            throw new ServiceException("未获取到该登录用户信息!");
        }
        String userId = userContent.getMemberId();
        if(null == userId){
            throw new ServiceException("当前登录用户信息不全，无法权限操作!");
        }
        //首次登录权限表没有改用户数据
        userPermissionsService.saveUserPermissions(userId,null);
        UserPermissions userPermissions = new UserPermissions();
        userPermissions = userPermissionsService.getUserPermissions(userId);
        return  userPermissions;
    }


    /**
     * 获取权限列表信息
     * @param selectInfo
     * @return
     */
    @RequestMapping(value = "/get/all/infos",method = RequestMethod.GET)
    public Result getAllUserInfos(@PathVariable(value = "selectInfo") String selectInfo) {
        String accessToken = yonZoneService.getAccessToken();
        if(accessToken.equals(null)){
            throw new ServiceException("未能获取到授权令牌!");
        }
        logger.info("accessToken data:{}",JSON.toJSONString(accessToken));
        List<UserContent> userContents = yonZoneService.getUserContentList(accessToken);
        if(CollectionUtils.isEmpty(userContents)){
            return ResultGenerator.genFailResult("没有获取到租户下所有用户信息!");
        }
        userContents.parallelStream().distinct().map(item ->{
            if(selectInfo.equals(null)){
                return item;
            }
            if(selectInfo.indexOf(item.getUserEmail())!=-1 || selectInfo.indexOf(item.getUserMobile()) !=-1 || selectInfo.indexOf(item.getUserId()) !=-1){
                return item;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        logger.info("租户下所有用户信息,data:{}", JSON.toJSONString(userContents));
        DiworkUsers diworkUsers = new DiworkUsers();
        diworkUsers.setContent(userContents);
        diworkUsers.setSize(Integer.toString(userContents.size()));
        diworkUsers.setTotalPages("1");
        return  ResultGenerator.genSuccessResult(diworkUsers);
    }



}
