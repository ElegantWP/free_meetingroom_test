package com.myweb.app.msgengine.msg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
/**
 * 消息确认机制。Confirms给客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，哪些可能因为broker宕掉或者网络失败的情况而重新发布。
 * 确认并且保证消息被送达，提供了两种方式：发布确认和事务。(两者不可同时使用)在channel为事务时，不可引入确认模式；同样channel为确认模式下，不可使用事务。
 * @author chenweiat
 *
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		log.info(" 回调:" + correlationData);
		if (ack) {
			log.info("消息成功消费");
		} else {
			log.info("消息消费失败:" + cause + "\n重新发送");
		}
	}
}
