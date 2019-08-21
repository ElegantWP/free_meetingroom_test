package com.myweb.app.controller;

import com.myweb.app.core.Result;
import com.myweb.app.core.ResultGenerator;
import com.myweb.app.entity.User;
import com.myweb.app.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator by weipan on 2019/08/20.
*/
@RestController
@RequestMapping("/user")
@Log
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation(value="增加user", notes="自动生成")
    @ApiImplicitParam(name = "user", value = "user", required = true, dataType = "User")
    @PostMapping
    public Result add(@RequestBody User user) {
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="根据id删除user", notes="自动生成")
    @ApiImplicitParam(name = "id", value = "userId", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="根据id更新user", notes="自动生成")
    @ApiImplicitParam(name = "user", value = "user", required = true, dataType = "User")
    @PutMapping
    public Result update(@RequestBody User user) {
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="根据id获取user详情", notes="自动生成")
    @ApiImplicitParam(name = "id", value = "userId", required = true, dataType = "string")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @ApiOperation(value="根据页码及单页数据条数获取user列表", notes="自动生成")
    @ApiImplicitParams({
        @ApiImplicitParam(dataType = "int", name = "pageIndex", value = "页码", required = true),
        @ApiImplicitParam(dataType = "int", name = "pageSize", value = "数据条数", required = true)
    })
    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
