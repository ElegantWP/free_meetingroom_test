package com.myweb.app.service;

import com.myweb.app.dto.MsgDto;
import com.myweb.app.dto.MsgGeneratorDto;
import com.myweb.app.dto.PushedListDto;
import com.myweb.app.rabbitmq.delay.DelayMsgPublisher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;


/**
 * 延迟消息发送Service
 * @author chenweiat
 *
 */

@Slf4j
@Service
public class DelayMsgSendService extends BaseMsgSendService{

	@Autowired
	private DelayMsgPublisher delayMsgPublisher;

	/**
	 * 延迟30分钟后发送消息
	 * @param pushedListDto
	 */
	public void pushMsgDelay30Minutes(PushedListDto pushedListDto) {
		checkData(pushedListDto);
		Timestamp targetTime = new Timestamp(new Date().getTime()+30*60*1000);
		long expire = targetTime.getTime() - System.currentTimeMillis();
		log.info("pushedListDto:"+pushedListDto);
		delayMsgPublisher.pushMsg(pushedListDto, expire);
	}

	public void checkData(PushedListDto pushedListDto){
		Assert.notNull(pushedListDto,"发送消息实体不能为空!");
		Assert.hasText(pushedListDto.getAccesstoken(),"accessToken不能为空!");
		Assert.hasText(pushedListDto.getUserId(),"userId不能为空!");
		Assert.hasText(pushedListDto.getTenantId(),"tenantId不能未空!");
		Assert.notNull(pushedListDto.getRoomId(),"roomId不能为空!");
	}

	/**
	 * 发送延迟消息
	 * 
	 * @param userId：userId
	 * @param msgTypeId:消息类型
	 * @param title：消息标题
	 * @param brief：消息简称
	 * @param content:消息内容
	 * @param bussinessParams：业务参数
	 * @param expire：指定时间
	 * @return 消息唯一ID
	 */
	public String pushMsgAtTargetTime(String userId, String msgTypeId, String title, String brief, String content, Map<String, String> bussinessParams, Timestamp targetTime) {
		MsgGeneratorDto msgGeneratorDto = generatorMsg(userId, title, brief, content, bussinessParams);
		if (StringUtils.isEmpty(msgGeneratorDto.getErrMsg())){
			long expire = targetTime.getTime() - System.currentTimeMillis();
			PushedListDto pushedListDto = msgGeneratorDto.getPushedListDto();
			log.info("pushedListDto:"+pushedListDto);
			return delayMsgPublisher.pushMsg(pushedListDto, expire);
		}else{

			return msgGeneratorDto.getErrMsg();
		}
	}
}
