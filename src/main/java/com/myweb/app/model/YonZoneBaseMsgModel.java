package com.myweb.app.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author weipan
 * @date 2019/8/24 19:52
 * @description
 */
@Getter
@Setter
public class YonZoneBaseMsgModel {

  /**
   * 租户id
   */
  private String tenantId;

  /**
   * 应用id
   */
  private String appId;

  /**
   * 文本内容
   */
  private String title;

  /**
   * 文本描述
   */
  private String content;

}
