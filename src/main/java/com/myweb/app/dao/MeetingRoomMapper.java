package com.myweb.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myweb.app.entity.MeetingRoom;
import com.myweb.app.entity.ScreenEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 * @author wanggqf
 * @date 2019/8/27
 * @description  会议室Mapper
 */
@Mapper
@Repository
public interface MeetingRoomMapper extends BaseMapper<MeetingRoom> {
    List<ScreenEntity> getScreen(int layer);

    List<ScreenEntity> getOneMeetingScreen(int id);
}
