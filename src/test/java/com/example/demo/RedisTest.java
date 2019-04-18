package com.example.demo;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	
	
	@Test
	public void testDemo() {
		
		redisTemplate.opsForValue().set("test63", "test63_redis");
		String out = redisTemplate.opsForValue().get("test63");
		System.out.println(out);
		
	}

}
