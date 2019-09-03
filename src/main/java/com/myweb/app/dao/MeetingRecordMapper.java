package com.myweb.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myweb.app.entity.MeetingRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author weipan
 * @date 2019/9/2 21:58
 * @description
 */
@Mapper
@Repository
public interface MeetingRecordMapper extends BaseMapper<MeetingRecord> {

}
