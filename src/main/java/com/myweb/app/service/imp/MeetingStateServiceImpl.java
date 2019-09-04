package com.myweb.app.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myweb.app.dao.MeetingRoomMapper;
import com.myweb.app.entity.MeetingRoom;
import com.myweb.app.service.MeetingStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 会议室状态服务
 * @author zhanghaoaj
 */
@Service
@RequiredArgsConstructor
public class MeetingStateServiceImpl implements MeetingStateService {

    private final MeetingRoomMapper mapper;

    /**
     * 会议室总数
     * @return count
     */
    @Override
    public int getList() {
        QueryWrapper<MeetingRoom> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("state");
        return mapper.selectCount(queryWrapper);
    }

    /**
     * 空闲中
     * @return count
     */
    @Override
    public int getFree() {
        QueryWrapper<MeetingRoom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 0);
        return mapper.selectCount(queryWrapper);
    }

    /**
     * 使用中
     * @return count
     */
    @Override
    public int getUsering() {
        QueryWrapper<MeetingRoom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 1);
        return mapper.selectCount(queryWrapper);
    }

    /**
     * 故障中
     * @return count
     */
    @Override
    public int getBreakdown() {
        QueryWrapper<MeetingRoom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 2);
        return mapper.selectCount(queryWrapper);
    }
}
