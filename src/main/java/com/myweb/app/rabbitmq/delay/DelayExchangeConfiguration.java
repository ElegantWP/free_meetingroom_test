package com.myweb.app.rabbitmq.delay;

import com.myweb.app.rabbitmq.conf.RabbitMqProperties;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义用于接受延迟消息的交换机
 * 
 * @author chenweiat
 *
 */
@Configuration
public class DelayExchangeConfiguration {

	@Autowired
	private RabbitMqProperties rabbitMqProperties;

	/**
	 * 定义用于接受延迟消息的交换机
	 * 
	 * @return
	 */
	@Bean("delayExchange")
	public DirectExchange delayExchange() {
		boolean durable = true;// 持久化exchange
		boolean autoDelete = false; // 当所有队列在完成使用此exchange时，是否自动删除，如果该队列没有任何订阅的消费者的话，该队列会被自动删除。这种队列适用于临时队列。
		DirectExchange delayExchange = new DirectExchange(rabbitMqProperties.getDelayMsgExchange(), durable, autoDelete);
		return delayExchange;
	}

}
