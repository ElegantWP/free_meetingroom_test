package com.myweb.app.rabbitmq.deadletter;

import com.myweb.app.rabbitmq.conf.RabbitMqProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *死信消息接受队列
 * 
 * @author chenweiat
 * 
 */
@Configuration
public class DeadLetterMsgQueueConfiguration {

	@Autowired
	private RabbitMqProperties rabbitMqProperties;
	
	@Autowired
	private DeadLetteMsgConsumer deadLetterMsgConsumer;

	/**
	 * 设置用于死信消息交换机的App延迟消息队列
	 * @return
	 */
	@Bean("deadLetterMsgQueue")
	public Queue deadLetterMsgQueue() {
		// 支持消息持久化
		boolean durable = true;
		// 表示该消息队列是否只在当前connection生效,默认是false
		boolean exclusive = false;
		// 表示消息队列没有在使用时将被自动删除 默认是false
		boolean autoDelete = false;
		Queue queue = new Queue(rabbitMqProperties.getDeadLetterMsgQueue(), durable, exclusive, autoDelete);
		return queue;
	}

	
	
	/**
	 * deadLetterFanoutExchange与deadLetterAppMsgQueue绑定
	 * 
	 * @return
	 */
	@Bean
	public Binding bindingDeadLetteMsgQueue(DirectExchange deadLetterExchange) {
		return BindingBuilder.bind(deadLetterMsgQueue()).to(deadLetterExchange).with(rabbitMqProperties.getDeadLetterMsgRoutingKey());
	}

	
	
	@Bean
	public SimpleMessageListenerContainer deadLetterMessageContainer(CachingConnectionFactory cachingConnectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);
		container.setQueues(deadLetterMsgQueue());
		container.setExposeListenerChannel(true);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(deadLetterMsgConsumer);
		return container;
	}

}
