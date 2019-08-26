package com.myweb.app.rabbitmq.conf;

import com.myweb.app.msgengine.msg.MsgSendConfirmCallBack;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
	
	@Autowired
	private RabbitMqProperties rabbitMqProperties;
	
	@Autowired
	private MsgSendConfirmCallBack msgSendConfirmCallBack;

	
	/**
	 * 声明rabbitmq连接池
	 * 
	 * @return
	 */
	@Bean("cachingConnectionFactory")
	public CachingConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMqProperties.getHost(), rabbitMqProperties.getPort());
		connectionFactory.setUsername(rabbitMqProperties.getUsername());
		connectionFactory.setPassword(rabbitMqProperties.getPassword());
		connectionFactory.setVirtualHost(rabbitMqProperties.getVirtualhost()); 
		connectionFactory.setPublisherConfirms(true);// 开启消息发送者，消息确认机制，相比事务确认
		return connectionFactory;
	}


	
	@Bean
	public RabbitTemplate rabbitTemplate(CachingConnectionFactory cachingConnectionFactory) {
		RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
		template.setMessageConverter(new Jackson2JsonMessageConverter());//数据转换为json存入消息队列
		/**
		 * 若使用confirm-callback或return-callback，必须要配置publisherConfirms或publisherReturns为true,每个rabbitTemplate只能有一个confirm-callback和return-callback
		 */
		template.setConfirmCallback(msgSendConfirmCallBack);
		/**
		 * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，可针对每次请求的消息去确定’mandatory’的boolean值，只能在提供’return
		 * -callback’时使用，与mandatory互斥
		 */
		// template.setReturnCallback(msgSendReturnCallback());
		// template.setMandatory(true);
		
		return template;
	}
}
