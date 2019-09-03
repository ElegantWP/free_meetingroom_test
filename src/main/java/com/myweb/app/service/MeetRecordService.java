package com.myweb.app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myweb.app.dao.MeetingRecordMapper;
import com.myweb.app.entity.MeetingRecord;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weipan
 * @date 2019/9/3 13:13
 * @description
 */
@Service
public class MeetRecordService {

  @Autowired
  private MeetingRecordMapper meetingRecordMapper;

  public List<MeetingRecord> getCreateIdByMeetingRoomId(List<String> roomId){
    List<MeetingRecord> meetingRecord = meetingRecordMapper.selectList(
        new QueryWrapper<MeetingRecord>().
            eq("meetingroom_id", roomId.get(0)));
        return meetingRecord;
  }

}
