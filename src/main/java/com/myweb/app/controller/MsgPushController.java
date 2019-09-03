package com.myweb.app.controller;

import com.myweb.app.config.AppConfig;
import com.myweb.app.core.Result;
import com.myweb.app.core.ResultGenerator;
import com.myweb.app.entity.MeetingRecord;
import com.myweb.app.model.AppCodeMsgModel;
import com.myweb.app.model.YonZoneMsgModel;
import com.myweb.app.service.MeetRecordService;
import com.myweb.app.service.YonZoneService;
import com.myweb.app.utils.Preconditions;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "推送会议信息到友空间")
@RestController
@Slf4j
@RequestMapping("rest/web/youZone")
public class MsgPushController {

  @Autowired
  private YonZoneService yonZoneService;

  @Autowired
  private AppConfig appConfig;

  @Autowired
  private MeetRecordService meetRecordService;

  @PostMapping("/state-change")
  public Result sendStateChangeMsg(@RequestBody YonZoneMsgModel model){
    Preconditions.checkNotNull(model,"传入对象为空");
    Preconditions.checkNotEmpty(model.getYhtUserIds(),"会议室的名称为空");
    List<MeetingRecord> createIdByMeetingRoomId = meetRecordService
        .getCreateIdByMeetingRoomId(model.getYhtUserIds());
    List<String> str = new ArrayList<>();
    createIdByMeetingRoomId.forEach(
        item ->{
          str.add(item.getCreatorId());
          String[] s = item.getInvolvedPersons().split(" ");
          Collections.addAll(str,s);
        }
    );
    AppCodeMsgModel servicesMsgList = yonZoneService
        .getServicesMsgList(yonZoneService.getAccessToken(), appConfig.getCode());
    model.setAppId(servicesMsgList.getYkjId());
    model.setTenantId(appConfig.getTenantId());
    model.setSendScope("list");
    model.setYhtUserIds(str);
    yonZoneService.sendNotifyShare(model,yonZoneService.getAccessToken());
    return ResultGenerator.genSuccessResult();
  }
}
