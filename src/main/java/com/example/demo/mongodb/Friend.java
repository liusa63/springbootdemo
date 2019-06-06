package com.example.demo.mongodb;

public class Friend {
	
	// 女
	public static final int SEX_FEMALE = 0; 
	//男
	public static final int SEX_MALE = 1;
	
	private String name;
	private Integer age;
	private Integer sex;
	
	
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
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "Friend [name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}
	
	
	

}
