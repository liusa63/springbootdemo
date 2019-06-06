package com.example.demo.mongodb;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class MongoTestUser {

	@Id
	private String id;
	
	@Field("name")
	private String name;
	
	@Field("age")
	private Integer age;
	
	@Field("tags")
	private List<String> tags;
	
	@Field("create_time")
	private Date createTime;
	
	@Field("friends")
	private List<Friend> friends;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return "MongoTesUser [id=" + id + ", name=" + name + ", age=" + age + ", tags=" + tags + ", createTime="
				+ createTime + "]";
	}
	
	

}
