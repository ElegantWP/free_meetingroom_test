package com.myweb.app.service;

import com.alibaba.fastjson.JSON;
import com.myweb.app.core.ServiceException;
import com.myweb.app.dto.MsgGeneratorDto;
import com.myweb.app.dto.PushedListDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
/**
 * 消息发送Service
 * @author chenweiat
 *
 */
@Slf4j
public class BaseMsgSendService {



	/**
	 * 生成待发送消息
	 * 
	 * @param userId：userId
	 * @param ：cid
	 * @param :消息类型
	 * @param title：消息标题
	 * @param brief：消息简称
	 * @param content:消息内容
	 * @param bussinessParams：业务参数
	 * @return
	 */
	protected MsgGeneratorDto generatorMsg(String userId, String title, String brief, String content, Map<String, String> bussinessParams) {
		MsgGeneratorDto ret = new MsgGeneratorDto();

		try {
			PushedListDto pushedListDto = generatorPushedListDto(userId, title, brief, content, bussinessParams);
			ret.setPushedListDto(pushedListDto);
		} catch (Exception e) {
			ret.setErrMsg(e.getMessage());
			log.error("生成待发送消息失败{}",ret.toString());
			throw new ServiceException("消息发送失败 请联系管理员重新再试");
		}
		return ret;
	}

	/**
	 * 生成发送消息内容
	 * 
	 * @param userId：userId
	 * @param title：消息标题
	 * @param brief：消息简称
	 * @param content:消息内容
	 * @param bussinessParams：业务参数
	 * @param ：消息类型
	 * @return
	 */
	protected PushedListDto generatorPushedListDto(String userId, String title, String brief, String content, Map<String, String> bussinessParams) {
		return generatorPushedListDto(userId, title, brief, content, JSON.toJSONString(bussinessParams));
	}

	/**
	 * 生成发送消息内容
	 * 
	 * @param userId：userId
	 * @param title：消息标题
	 * @param brief：消息简称
	 * @param content:消息内容
	 * @param bussinessParams：业务参数
	 * @param ：消息类型
	 * @return
	 */
	protected PushedListDto generatorPushedListDto(String userId, String title, String brief, String content, String bussinessParams) {
		PushedListDto pushedListDto = new PushedListDto();
		pushedListDto.setUserId(userId);// 用户Id
		pushedListDto.setTitle(title);// 消息标题
		pushedListDto.setBrief(brief);// 消息摘要
		pushedListDto.setContent(content);// 消息内容
		pushedListDto.setBussinessParams(bussinessParams);
		return pushedListDto;
	}

}
