package com.myweb.app.model;

import io.swagger.annotations.ApiModelProperty;
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
  @ApiModelProperty("选填")
  private String tenantId;

  /**
   * 应用id
   */
  @ApiModelProperty("选填")
  private String appId;

  /**
   * 文本内容
   */
  @ApiModelProperty("发送文本消息的标题")
  private String title;

  /**
   * 文本描述
   */
  @ApiModelProperty("文本消息的内容")
  private String content;

}
