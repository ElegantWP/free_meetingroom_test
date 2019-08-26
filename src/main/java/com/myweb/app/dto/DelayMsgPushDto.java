package com.myweb.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DelayMsgPushDto implements java.io.Serializable{

	/**
	 * 发送时间 yyyy-MM-dd hh24:mi:ss
	 */
	private String targetTime;
	
	/**
	 * 接收消息用户列表
	 */
	private List<String> userIds;
	
	/**
	 * 接收消息用户列表
	 */
	private MsgDto msg;
}
