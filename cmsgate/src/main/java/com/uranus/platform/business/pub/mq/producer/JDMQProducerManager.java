package com.uranus.platform.business.pub.mq.producer;

import javax.annotation.Resource;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.uranus.platform.business.pub.mq.domain.MQParmsDomain;

@Service
public class JDMQProducerManager {
	@Resource
    private RocketMQTemplate rocketMQTemplate;
	
	/**  
	* @Fields topicName
	*/  
	@Value("${rocketmq.topic}")
	private String topicName;
	
	/**  
	* @Fields 发送MQ消息超时时间
	*/  
	@Value("${rocketmq.sendTimeout}")
	private long timeout;
	
	
	/**  
	* @Description 将消息发送到MQ队列中
	* @param 消息对象
	*/  
	public void send(MQParmsDomain message) {
		 rocketMQTemplate.syncSend(topicName, MessageBuilder.withPayload(message).build(), timeout, message.getDelayLevel());
	}
}
