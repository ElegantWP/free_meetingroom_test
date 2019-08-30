package com.myweb.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myweb.app.entity.MeetingRoom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wanggqf
 * @date 2019/8/27
 * @description  会议室Mapper
 */
@Mapper
@Repository
public interface MeeingRoomMapper extends BaseMapper<MeetingRoom> {

}
