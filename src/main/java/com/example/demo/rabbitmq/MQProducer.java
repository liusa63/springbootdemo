package com.example.demo.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQProducer {
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	/**
	 * @param queue  队列名
	 * @param content  消息内容
	 */
	public void sendMessage(String queue,String content) {
		
			rabbitTemplate.convertAndSend(queue, content);
        
    }

}