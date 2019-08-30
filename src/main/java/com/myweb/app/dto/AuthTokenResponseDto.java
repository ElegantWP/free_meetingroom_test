package com.myweb.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author weipan
 * @date 2019/8/23 20:12
 * @description
 */

@Getter
@Setter
public class AuthTokenResponseDto implements Serializable {

  @ApiModelProperty("授权令牌")
  @JsonProperty("access_token")
  private String accessToken;

  private Integer expire;

}
