package com.myweb.app.controller;

import com.myweb.app.core.Result;
import com.myweb.app.core.ResultGenerator;
import com.myweb.app.service.MeetingStateService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据统计接口（统计会议室总数、空闲中（包括未开始和已结束的会议室）、使用中、故障中）
 *
 * @author zhanghaoaj
 */
@Api(value = "数据统计接口")
@RestController
@RequestMapping("/rest/state")
@RequiredArgsConstructor
public class MeetingStateController {

    private final MeetingStateService stateService;

    /**
     * 会议室总数
     * @return ResultGenerator
     */
    @GetMapping("/list")
    public Result getList() {
        int result = stateService.getList();
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 空闲中
     * @return ResultGenerator
     */
    @GetMapping("/free")
    public Result getFree() {
        int result = stateService.getFree();
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 使用中
     * @return ResultGenerator
     */
    @GetMapping("/use")
    public Result getUsering() {
        int result = stateService.getUsering();
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 故障中
     * @return ResultGenerator
     */
    @GetMapping("/breakdown")
    public Result getBreakdown() {
        int result = stateService.getBreakdown();
        return ResultGenerator.genSuccessResult(result);
    }
}
