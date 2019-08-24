package com.myweb.app.service;

import com.myweb.app.model.YonZoneMsgModel;

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
  String sendNotifyShare(YonZoneMsgModel model, String accessToken);


}
