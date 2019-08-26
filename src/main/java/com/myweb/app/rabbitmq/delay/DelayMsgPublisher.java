package com.myweb.app.rabbitmq.delay;

import com.myweb.app.dto.PushedListDto;
import com.myweb.app.rabbitmq.conf.RabbitMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 延迟消息发送者
 * 
 * @author chenweiat
 *
 */

@Slf4j
@Component
public class DelayMsgPublisher {

	@Autowired
	private RabbitMqProperties rabbitMqProperties;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 发送延迟消息
	 * 
	 * @param pushedListDto：消息内容
	 * @param expire：延时时间（毫秒数）
	 * @return 消息唯一ID
	 */
	public String pushMsg(PushedListDto pushedListDto, long expire) {
		String ret = null;
		if (expire > 0) {
			CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
			rabbitTemplate.convertAndSend(rabbitMqProperties.getDelayMsgExchange(), rabbitMqProperties.getDelayMsgRoutingKey(), pushedListDto, new MessagePostProcessor() {
				@Override
				public Message postProcessMessage(Message message) throws AmqpException {
					message.getMessageProperties().setExpiration(expire + "");
					return message;
				}
			}, correlationData);
			ret = correlationData.getId();
		}
		return ret;
	}

}
