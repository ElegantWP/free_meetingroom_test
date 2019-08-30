package com.myweb.app.dto;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author weipan
 * @date 2019/8/23 20:14
 * @description
 */
@Getter
@Setter
public class BaseEntity<T> implements Serializable {

  private String code;

  private String message;

  private T data;
}
