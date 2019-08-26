package com.myweb.app.rabbitmq.deadletter;

import com.myweb.app.rabbitmq.conf.RabbitMqProperties;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeadLetterExchangeConfiguration {


	@Autowired
	private RabbitMqProperties rabbitMqProperties;
	
	/**
	 * 定义用于死信交换机
	 * 
	 * @return
	 */
	@Bean("deadLetterExchange")
	public DirectExchange deadLetterExchange() {
		boolean durable = true;// 持久化exchange
		boolean autoDelete = false; // 当所有队列在完成使用此exchange时，是否自动删除，如果该队列没有任何订阅的消费者的话，该队列会被自动删除。这种队列适用于临时队列。
		DirectExchange deadLetterExchange = new DirectExchange(rabbitMqProperties.getDeadLetterMsgExchange(), durable, autoDelete);
		return deadLetterExchange;
	}
}
