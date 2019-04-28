package com.javastudy.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.javastudy.system.BaseEntity;

@Document
public class Student extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 920289544365252831L;
	
	@Id
	private String id;
	
	@Field
	private String name;
	
	@Field
	private Integer age;
	
	@Field
	private String sex;
	
	public Student(String id, String name, Integer age, String sex) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	

}
