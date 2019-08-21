package com.myweb.app.service.impl;

import com.myweb.app.dao.UserMapper;
import com.myweb.app.entity.User;
import com.myweb.app.service.UserService;
import com.myweb.app.core.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator by weipan on 2019/08/20.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

}
