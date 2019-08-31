package com.myweb.app.controller;

import com.myweb.app.core.Result;
import com.myweb.app.core.ResultGenerator;
import com.myweb.app.dao.UserMapper;
import com.myweb.app.entity.User;
import com.myweb.app.service.YonZoneService;
import com.myweb.app.utils.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weipan
 * @date 2019/8/24 20:50
 * @description
 */
@RestController
public class TestController {

  @Autowired
  UserMapper userMapper;

  @Autowired
  private YonZoneService yonZoneService;

  @GetMapping("/user")
  public Result addUser(){
    String accessToken = yonZoneService.getAccessToken();
    return ResultGenerator.genSuccessResult().setData(accessToken);
  }

  public Result select(@RequestParam Integer id){
    Preconditions.checkNotNull(id,"参数有误");
    return ResultGenerator.genSuccessResult(userMapper.selectById(id));
  }
}
