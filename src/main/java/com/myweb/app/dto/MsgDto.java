package com.myweb.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class MsgDto {
	
	/**
	 * 用户Id
	 */
	private String userId;
	
	/**
	 * 消息类型
	 */
	private String msgTypeId;
	
	/**
	 * 消息标题
	 */	
	private String title;
	
	/**
	 * 消息摘要
	 */
	private String brief;
	
	/**
	 * 消息内容
	 */
	private String content;
	
	/**
	 * 消息业务参数
	 */
	private Map<String, String> bussinessParams = new HashMap<String, String>();
}
