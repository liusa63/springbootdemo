package com.example.demo;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.rabbitmq.MQProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqTest {
	
	@Resource
	private MQProducer producer;
	
	
	@Test
	public void testDemo() {
		
		producer.sendMessage("testQueue", "testContent");
		
	}

}
