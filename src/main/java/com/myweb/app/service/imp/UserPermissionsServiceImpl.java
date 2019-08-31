package com.myweb.app.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myweb.app.core.Result;
import com.myweb.app.core.ResultGenerator;
import com.myweb.app.dao.UserPermissionsMapper;
import com.myweb.app.entity.MeetingRoom;
import com.myweb.app.entity.UserPermissions;
import com.myweb.app.enums.UserPermissionsTypeEnum;
import com.myweb.app.service.UserPermissionsService;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author nilg
 * @since 2019/8/31 23:51
 */
@Service
public class UserPermissionsServiceImpl implements UserPermissionsService {
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
        //新增，默认权限为普通用户
        UserPermissions userPermissions = new UserPermissions();
        userPermissions.setUserId(userId);
        userPermissions.setType(UserPermissionsTypeEnum.find(1));
        userPermissions.setCreatedTime(LocalDateTime.parse(LocalDateTime.now().toString(),df));
        //若改用户权限已存在则更新
        if(null != getUserPermissions(userId)){
            userPermissions= getUserPermissions(userId);
            //若权限类型不为空则更新，否则保持原权限类型
            if(null !=type){
                userPermissions.setType(UserPermissionsTypeEnum.find(type));
                userPermissions.setModifyTime(LocalDateTime.parse(LocalDateTime.now().toString(),df));
            }
        }
        int resultNum = mapper.insert(userPermissions);
        Result result = new Result();
        return  result = resultNum==1 ? ResultGenerator.genSuccessResult():ResultGenerator.genFailResult("保存用户权限失败!");
    }
}
