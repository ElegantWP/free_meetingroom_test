package com.myweb.app.rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myweb.app.dto.PushedListDto;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;


@Slf4j
public class BaseMsgConsumer implements ChannelAwareMessageListener {

	
	/**
	 * 端消息接收者，消费消息队列中推送消息，调用个推接口向前端App发送，发送成功后持久化存储（异步）
	 */
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
			String body = new String(message.getBody());
			ObjectMapper mapper = new ObjectMapper();
			PushedListDto pushedListDto = mapper.readValue(body, PushedListDto.class);
			log.info("receive msg : " + body);
			pushedListDto.setIsRead(0);// 是否已读 0：未读，1：已读
			//消费消息
			System.out.println(pushedListDto.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 发送回执
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 确认消息成功消费
	}

}
