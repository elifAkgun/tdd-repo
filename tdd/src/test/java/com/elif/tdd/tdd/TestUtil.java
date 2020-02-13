package com.elif.tdd.tdd;

import com.elif.tdd.tdd.user.User;

public class TestUtil {

	public static User crateValidUser() {
		User user = new User();
		user.setUsername("test-user");
		user.setDisplayName("test-display");
		user.setPassword("asAS1234");
		user.setImage("profile-image.png");
		return user;
	}
	
	public static User crateValidUser(String username) {
		User user = crateValidUser();
		user.setUsername(username);
		return user;
	}
}
