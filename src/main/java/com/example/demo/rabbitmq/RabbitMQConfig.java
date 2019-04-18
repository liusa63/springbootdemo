package com.example.demo.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
	
    
    
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }
    
    /**
     * 连接工厂
     * @author liusa
     * @date 2019/04/18 17:04
     * @param connectionFactory
     * @return
     */
    @Bean
	public SimpleRabbitListenerContainerFactory myMQFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		//设置手动应答
//		factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		return factory;
	}



}
