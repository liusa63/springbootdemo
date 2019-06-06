package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.demo.mongodb.Friend;
import com.example.demo.mongodb.MongoAggregation;
import com.example.demo.mongodb.MongoTestUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoAggregationTest {
	
	
	@Autowired
	private MongoAggregation mongoAggregation;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@Test
	public void testDemo() {
//		getAllFriendsWithSameLastName("123","zhao");
//		getFriendsWithSameAge("123",21,3,0);
		getSumFemaleAgeWithSameLastName("123","zhao",1);
	}
	
	
	public void getAllFriendsWithSameLastName(String id, String lastName) {
		List<Friend> list = mongoAggregation.getAllFriendsWithSameLastName(id, lastName);
		if(list != null) {
			for(Friend friend:list) {
				System.out.println(friend.toString());
			}
		}
	}
	
	public void getFriendsWithSameAge(String id, Integer age, Integer limit, Integer offset) {
		List<Friend> list = mongoAggregation.getFriendsWithSameAge(id, age, limit, offset);
		if(list != null) {
			for(Friend friend:list) {
				System.out.println(friend.toString());
			}
		}
	}
	
	public void getSumFemaleAgeWithSameLastName(String id, String lastName,Integer sex) {
		Long sum = mongoAggregation.getSumFemaleAgeWithSameLastName(id, lastName, sex);
		System.out.println(sum);
	}
	
	
	/*
	 * **************** 初始后数据库数据 ***************
	 * 
	 * > db.mongoTestUser.find({"_id":"123"}).pretty();
		{
			"_id" : "123",
			"name" : "liusa-123",
			"age" : 23,
			"tags" : [
				"java",
				"mongodb",
				"spring",
				"python"
			],
			"create_time" : ISODate("2019-06-06T08:37:09.756Z"),
			"friends" : [
				{
					"name" : "liu0",
					"age" : 20,
					"sex" : 0
				},
				{
					"name" : "liu1",
					"age" : 21,
					"sex" : 1
				},
				{
					"name" : "liu2",
					"age" : 22,
					"sex" : 0
				},
				{
					"name" : "zhao0",
					"age" : 20,
					"sex" : 1
				},
				{
					"name" : "zhao1",
					"age" : 21,
					"sex" : 0
				},
				{
					"name" : "zhao2",
					"age" : 22,
					"sex" : 1
				},
				{
					"name" : "zhao3",
					"age" : 23,
					"sex" : 0
				},
				{
					"name" : "zhao4",
					"age" : 24,
					"sex" : 1
				},
				{
					"name" : "wang0",
					"age" : 20,
					"sex" : 0
				},
				{
					"name" : "wang1",
					"age" : 21,
					"sex" : 1
				}
			],
			"_class" : "com.example.demo.mongodb.MongoTestUser"
		}

	 * 
	 * 
	 */
	
	
	
	
	/**
	 * 初始化数据
	 * @author liusa
	 * @date 2019/06/06 16:10
	 */
	public void initUser() {
		MongoTestUser user = new MongoTestUser();
		user.setId("123");
		user.setName("liusa-123");
		user.setAge(23);
		user.setTags(Arrays.asList("java", "mongodb", "spring","python"));
		user.setCreateTime(new Date());
		
		List<Friend> friends = new  ArrayList<Friend>();
		
		for (int i = 0; i < 3; i++) {
			Friend friend = new Friend();
			friend.setName("liu"+i);
			friend.setAge(20+i);
			if(i % 2 == 0) {
				friend.setSex(Friend.SEX_FEMALE);
			}else {
				friend.setSex(Friend.SEX_MALE);
			}
			friends.add(friend);
		}
		
		for (int i = 0; i < 5; i++) {
			Friend friend = new Friend();
			friend.setName("zhao"+i);
			friend.setAge(20+i);
			if(i % 2 == 1) {
				friend.setSex(Friend.SEX_FEMALE);
			}else {
				friend.setSex(Friend.SEX_MALE);
			}
			friends.add(friend);
		}
		
		for (int i = 0; i < 2; i++) {
			Friend friend = new Friend();
			friend.setName("wang"+i);
			friend.setAge(20+i);
			if(i % 2 == 0) {
				friend.setSex(Friend.SEX_FEMALE);
			}else {
				friend.setSex(Friend.SEX_MALE);
			}
			friends.add(friend);
		}
		
		user.setFriends(friends);
		
		mongoTemplate.save(user);
	}
	
	
	

}
