package com.example.demo.excel;

import java.util.Date;

public class ExcelEntity {
	
	private String name;
	private int age;
	private String brithday;
	
	
	public ExcelEntity(String name, int age, String string) {
		super();
		this.name = name;
		this.age = age;
		this.brithday = string;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getBrithday() {
		return brithday;
	}
	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}

}
