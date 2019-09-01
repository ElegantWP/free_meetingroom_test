package com.myweb.app.service.imp;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myweb.app.core.Result;
import com.myweb.app.core.ResultGenerator;
import com.myweb.app.core.ServiceException;
import com.myweb.app.dao.UserPermissionsMapper;
import com.myweb.app.entity.MeetingRoom;
import com.myweb.app.entity.UserPermissions;
import com.myweb.app.enums.UserPermissionsTypeEnum;
import com.myweb.app.service.UserPermissionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author nilg
 * @since 2019/8/31 23:51
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserPermissionsServiceImpl implements UserPermissionsService {
    Logger logger = LoggerFactory.getLogger(UserPermissionsServiceImpl.class);

    private UserPermissionsMapper mapper;
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * 查询当前用户权限
     * @param userId
     * @return
     */
    @Override
    public UserPermissions getUserPermissions(Long userId) {
        QueryWrapper<UserPermissions> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(null !=userId,"userId",userId);
        logger.info("【UserPermissionsServiceImpl】【getUserPermissions】查询条件  queryWrapper：{}",JSON.toJSONString(queryWrapper));
        return mapper.selectOne(queryWrapper);
    }

    /**
     * 保存权限（新增,更新）
     * @param userId
     * @param type
     * @return
     */
    @Override
    public Result saveUserPermissions(Long userId,Integer type) {
        Boolean update = false;
        //新增，默认权限为普通用户
        UserPermissions userPermissions = new UserPermissions();
        userPermissions.setUserId(userId);
        userPermissions.setType(UserPermissionsTypeEnum.find(1));
        userPermissions.setCreatedTime(LocalDateTime.parse(LocalDateTime.now().toString(),df));
        //若改用户权限已存在则更新
        if(null != getUserPermissions(userId)){
            update = true;
            userPermissions= getUserPermissions(userId);
            if(null == userPermissions){
                throw new ServiceException("查找当前用户信息失败!");
            }
            logger.info("【UserPermissionsServiceImpl】【saveUserPermissions】原数据权限信息：{}",JSON.toJSONString(userPermissions));
            //若权限类型不为空则更新，否则保持原权限类型
            if(null !=type){
                userPermissions.setType(UserPermissionsTypeEnum.find(type));
                userPermissions.setModifyTime(LocalDateTime.parse(LocalDateTime.now().toString(),df));
            }
        }
        String saveStatus = "新增权限";
        saveStatus = update==true?"更新权限":saveStatus;
        logger.info("【UserPermissionsServiceImpl】【saveUserPermissions】保存前权限对象信息,操作名称;{},userPermissions：{}",saveStatus,JSON.toJSONString(userPermissions));
        int resultNum = 0;
        if(update){
            if(null !=type){
                resultNum= mapper.updateById(userPermissions);
            }
        }
        if(!update){
           resultNum= mapper.insert(userPermissions);
        }
        Result result = new Result();
        return  result = resultNum==1 ? ResultGenerator.genSuccessResult():ResultGenerator.genFailResult("保存用户权限失败!");
    }
}
