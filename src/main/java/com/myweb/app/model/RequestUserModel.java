package com.myweb.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author weipan
 * @date 2019/8/28 15:20
 * @description
 */
@Getter
@Setter
@Builder
public class RequestUserModel {

  /**
   * index : 1
   * size : 10
   * sortType : userName
   */

  private String index;
  private String size;
  private String sortType;
  @JsonProperty("access_token")
  private String accessToken;


}
