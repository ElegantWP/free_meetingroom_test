package com.myweb.app.rabbitmq.delay;

import com.myweb.app.rabbitmq.conf.RabbitMqProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延迟队列：RabbitMQ的Queue可以配置x-dead-letter-exchange
 * 和x-dead-letter-routing-key（可选）两个参数，如果队列内出现了dead letter，则按照这两个参数重新路由转发到指定的队列。
 * 
 * @author chenweiat
 * 
 */
@Configuration
public class DelayQueueConfiguration {

	@Autowired
	private RabbitMqProperties rabbitMqProperties;

	/**
	 * 设置用于接受延迟消息交换机的延迟消息队列
	 * @return
	 */
	@Bean("delayMsgQueue")
	public Queue delayMsgQueue() {
		// 支持消息持久化
		boolean durable = true;
		// 表示该消息队列是否只在当前connection生效,默认是false
		boolean exclusive = false;
		// 表示消息队列没有在使用时将被自动删除 默认是false
		boolean autoDelete = false;
		// 设置死信消息交换机
		Map<String, Object> arguments = new HashMap<>();
		// x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
		arguments.put("x-dead-letter-exchange", rabbitMqProperties.getDeadLetterMsgExchange());
		// x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
		arguments.put("x-dead-letter-routing-key", rabbitMqProperties.getDeadLetterMsgRoutingKey());
		//x-message-ttl 设置整体队列的过期时间 单位毫秒，暂不设置。目前产生死信均靠消息超时时间，有消息积压风险
		//arguments.put("x-message-ttl", new Integer(rabbitMqProperties.getDelayQueueMessageTtl()));
		Queue queue = new Queue(rabbitMqProperties.getDelayMsgQueue(), durable, exclusive, autoDelete,arguments);
		return queue;
	}
	
	
	/**
	 * delayDirectExchange与delayQueue绑定
	 * 
	 * @return
	 */
	@Bean
	public Binding bindingDelayMsgQueue(DirectExchange delayExchange) {
		return BindingBuilder.bind(delayMsgQueue()).to(delayExchange).with(rabbitMqProperties.getDelayMsgRoutingKey());
	}

}
