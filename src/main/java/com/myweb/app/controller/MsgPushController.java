package com.myweb.app.controller;

import com.myweb.app.config.AppConfig;
import com.myweb.app.core.Result;
import com.myweb.app.core.ResultGenerator;
import com.myweb.app.model.AppCodeMsgModel;
import com.myweb.app.model.YonZoneMsgModel;
import com.myweb.app.service.YonZoneService;
import com.myweb.app.utils.Preconditions;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "推送会议信息到友空间")
@RestController
@RequestMapping("rest/web/youZone")
public class MsgPushController {

  @Autowired
  private YonZoneService yonZoneService;

  @Autowired
  private AppConfig appConfig;

  @PostMapping("/state-change")
  public Result sendStateChangeMsg(@RequestBody YonZoneMsgModel model){
    Preconditions.checkNotNull(model,"传入对象为空");
    AppCodeMsgModel servicesMsgList = yonZoneService
        .getServicesMsgList(yonZoneService.getAccessToken(), appConfig.getCode());
    model.setAppId(servicesMsgList.getYkjId());
    model.setTenantId(appConfig.getTenantId());
    model.setSendScope("list");
    yonZoneService.sendNotifyShare(model,yonZoneService.getAccessToken());
    return ResultGenerator.genSuccessResult();
  }
}
