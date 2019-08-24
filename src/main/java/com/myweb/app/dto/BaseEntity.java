package com.myweb.app.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author weipan
 * @date 2019/8/23 20:14
 * @description
 */
@Getter
@Setter
public class BaseEntity {

  private String code;

  private String message;

  private Object data;
}
