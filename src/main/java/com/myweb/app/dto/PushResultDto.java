package com.myweb.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PushResultDto implements java.io.Serializable {
	/**
	 * 返回结果
	 */
	private String result;
	/**
	 * 发送内容唯一标识
	 */
	private String contentId;
}
