package com.anhkhoa.repository;

import java.util.ArrayList;
import java.util.List;

import com.anhkhoa.model.User;

public class TempUserRepository {
	List<User> userList;

	public TempUserRepository() {
		userList = new ArrayList<>();

		User user1 = new User(1, "Anh Khoa", "Male", "Active");
		User user2 = new User(2, "Ngoc Han", "Female", "Active");
		userList.add(user1);
		userList.add(user2);
	}

	public List<User> getUsers() {
		return this.userList;
	}

	public User add(User user) {
		userList.add(user);
		return user;
	}

	public User getUser(Integer id) {
		for (User user : userList) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}
}
