package com.myweb.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author weipan
 * @date 2019/8/28 11:36
 * @description
 */
@Getter
@Setter
@ToString
public class UserContent {

  private String userId;
  private String userCode;
  private String userName;
  private String userMobile;
  private String userMobileCountrycode;
  private String userEmail;
  private String userActivate;
  private String registerDate;
  private String loginTs;
  private String userAvatorNew;
  private String userBigAvatorNew;
  private String userSmallAvatorNew;
  private String sysId;

}
