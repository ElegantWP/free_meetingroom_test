package com.myweb.app.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myweb.app.core.Result;
import com.myweb.app.core.ResultGenerator;
import com.myweb.app.dao.MeeingRoomMapper;
import com.myweb.app.entity.MeetingRoom;
import com.myweb.app.entity.ScreenEntity;
import com.myweb.app.service.MeetingRoomService;
import com.myweb.app.utils.PageCondition;
import com.myweb.app.utils.Preconditions;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(value = "对会议室操作相关")
@RestController
@RequestMapping("/rest/web/meetingroom")
@RequiredArgsConstructor
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;
    @Autowired
    MeeingRoomMapper mapper;
    /**
     * 添加会议室
     */
    @PostMapping("/add")
    public Result addMeetingRoom(@RequestBody MeetingRoom room){
        Preconditions.checkNotNull(room,"参数有误");
        int i = meetingRoomService.add(room);
        return i == 1 ? ResultGenerator.genSuccessResult(room) : ResultGenerator.genFailResult("添加会议室失败");
    }
    /**
     * 修改会议室
     */
    @PostMapping("/update")
    public Result updateMeetingRoom(@RequestBody MeetingRoom room){
        Preconditions.checkNotNull(room,"参数有误");
        int i = meetingRoomService.update(room);
        return i == 1 ? ResultGenerator.genSuccessResult(room) : ResultGenerator.genFailResult("修改会议室信息失败");
    }
    /**
     * 更改会议室状态，使用会议室，将状态从可用变为使用中
     */
    @GetMapping("/use")
    public Result useMeetingRoom(@RequestParam Integer id){
        Preconditions.checkNotNull(id,"参数有误");
        int i = meetingRoomService.use(id);
        return i == 1 ? ResultGenerator.genSuccessResult() : ResultGenerator.genFailResult("使用会议室，变更状态失败");
    }
    /**
     * 更改会议室状态，释放会议室，将状态从使用中变为可用
     */
    @GetMapping("/release")
    public Result relaseMeetingRoom(@RequestParam Integer id){
        Preconditions.checkNotNull(id,"参数有误");
        int i = meetingRoomService.release(id);
        return i == 1 ? ResultGenerator.genSuccessResult() : ResultGenerator.genFailResult("释放会议室，变更状态失败");
    }

    /**
     * 删除会议室
     */
    @GetMapping("/delete")
    public Result updateMeetingRoom(@RequestParam Integer id){
        Preconditions.checkNotNull(id,"参数有误");
        int i = meetingRoomService.disable(id);
        return i == 1 ? ResultGenerator.genSuccessResult(): ResultGenerator.genFailResult("停用会议室，变更状态失败");
    }

    /**
     * 查询会议室
     */
    //按照id查询会议室
    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer id){
        Preconditions.checkNotNull(id,"参数有误");
        MeetingRoom room = meetingRoomService.selectById(id);
        return room != null?ResultGenerator.genSuccessResult(room):ResultGenerator.genFailResult("未查找到相关会议室");
    }

    //按照其他条件 分页查询
    @PostMapping("/select")
    public Result select(@RequestBody PageCondition pageCondition){

        IPage<MeetingRoom> rtn = meetingRoomService.selectPage(pageCondition);
        return ResultGenerator.genSuccessResult(rtn);
    }

    //会议室的投屏信息
    @GetMapping("/getScreen")
    public Result getScreen(Integer layer,Integer currentPage){//那座，第几页
        IPage<ScreenEntity> rtn = meetingRoomService.getScreen(layer,currentPage);
        return  ResultGenerator.genSuccessResult(rtn);
    }
}
