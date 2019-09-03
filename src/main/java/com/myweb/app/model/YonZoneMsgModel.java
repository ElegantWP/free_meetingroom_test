package com.myweb.app.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author weipan
 * @date 2019/8/24 19:54
 * @description
 */
@Getter
@Setter
public class YonZoneMsgModel extends YonZoneBaseMsgModel{

  /**
   * 有地址时点击跳转: 移动端打开地址
   */
  @ApiModelProperty("可选")
  private String url;

  /**
   * 有地址时点击跳转: WEB打开地址
   */
  @ApiModelProperty("可选")
  private String webUrl;

  /**
   * 接收范围，list根据to发送
   */
  @ApiModelProperty("发送人为多个时 为list")
  private String sendScope;

  /**
   * sendScope=list时，接收人范围，yhtUserIds列表结构，用户ID列表
   */
  @ApiModelProperty("sendScope=list时，接收人范围，yhtUserIds列表结构，用户ID列表")
  private List<String> yhtUserIds;
}
