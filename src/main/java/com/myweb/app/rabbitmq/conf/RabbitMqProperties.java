package com.myweb.app.rabbitmq.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rabbit.mq")
@Getter
@Setter
public class RabbitMqProperties {
	/**
	 * host
	 */
	private String host;
	/**
	 * port
	 */
	private Integer port;
	/**
	 * username
	 */
	private String username;
	/**
	 * password
	 */
	private String password;
	/**
	 * virtualhost
	 */
	private String virtualhost;
	/**
	 * msgExchange
	 */
	private String msgExchange;
	/**
	 * msgQueue
	 */
	private String msgQueue;
	/**
	 * msgRoutingKey
	 */
	private String msgRoutingKey;
	/**
	 * delayMsgExchange
	 */
	private String delayMsgExchange;
	/**
	 * delayMsgQueue
	 */
	private String delayMsgQueue;
	/**
	 * delayQueueMessageTtl
	 */
	private String delayMsgQueueMessageTtl;
	/**
	 * delayMsgRoutingKey
	 */
	private String delayMsgRoutingKey;
	/**
	 * deadLetterMsgExchange
	 */
	private String deadLetterMsgExchange;
	/**
	 * deadLetterMsgQueue
	 */
	private String deadLetterMsgQueue;	
	/**
	 * deadLetterMsgRoutingKey
	 */
	private String deadLetterMsgRoutingKey;		
		
	/**
	 * eventMsgExchange
	 */
	private String eventMsgExchange;
	/**
	 * eventMsgQueue
	 */
	private String eventMsgQueue;
	/**
	 * eventMsgRoutingKey
	 */
	private String eventMsgRoutingKey;

}
