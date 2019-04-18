package com.example.demo.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQCustomer {
	
	
	private static final Logger logger = LoggerFactory.getLogger(MQCustomer.class);
	

	@RabbitListener(containerFactory="myMQFactory",queues = "demo_testQueue")
    public void receive1(String content){
		
		logger.info("receive1消费了---------- {}",content);
		try {
			Thread.sleep(1*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
    }
	
}