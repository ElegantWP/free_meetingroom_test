package com.myweb.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MsgGeneratorDto implements Serializable {

	/**
	 * 错误消息
	 */
	private String errMsg;
	
	/**
	 * 待发送消息
	 */
	private PushedListDto pushedListDto = null;
}
