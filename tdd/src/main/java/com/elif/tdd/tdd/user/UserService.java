package com.elif.tdd.tdd.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//this anotation is stereotype annotation 
//We are telling sprint to create an instance of this class now.
@Service
public class UserService {

	UserRepostory repostory;

	BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserService(UserRepostory repostory) {
		super();
		this.repostory = repostory;
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
	}

	public User save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return repostory.save(user);
	}
}
