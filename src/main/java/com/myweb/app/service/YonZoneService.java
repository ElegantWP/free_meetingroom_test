package com.myweb.app.service;

import com.myweb.app.dto.YouZoneUser;
import com.myweb.app.model.AppCodeMsgModel;
import com.myweb.app.model.UserContent;
import com.myweb.app.model.YonZoneMsgModel;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 * @author weipan
 * @date 2019/8/23 20:05
 * @description
 */
public interface YonZoneService {


  /**
   * 获取授权令牌
   * @return
   */
  String getAccessToken();

  /**
   * 发送代办通知
   * @param model
   * @param accessToken
   * @return
   */
  ResponseEntity<String> sendNotifyShare(YonZoneMsgModel model, String accessToken);

  /**
   * 根据APPCode 获取服务信息列表 推送消息会使用
   * @param token
   * @param appcode
   * @return
   */
  AppCodeMsgModel getServicesMsgList(String token,String appcode);

  List<UserContent> getUserContentList(String accessToken);

  YouZoneUser getUserContent(String code,String accessToken);
}
