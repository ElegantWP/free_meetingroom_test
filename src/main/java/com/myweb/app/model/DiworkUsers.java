package com.myweb.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author weipan
 * @date 2019/8/28 11:33
 * @description
 */
@Getter
@Setter
@ToString
public class DiworkUsers {


  private String number;
  private String size;
  private String totalPages;
  @JsonProperty("content")
  private List<UserContent> content;


}
