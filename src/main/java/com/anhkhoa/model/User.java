package com.anhkhoa.model;

public class User {
	private int id;
	private String name;
	private String gender;
	private String status;
	
	public User() {
		
	}

	public User(int id, String name, String gender, String status) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "{ id: " + id + ", name: " + name + ", gender: " + gender + ", status: " + status + " }";
	}
}
