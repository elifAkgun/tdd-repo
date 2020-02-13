package com.elif.tdd.tdd.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//this anotation is stereotype annotation 
//We are telling sprint to create an instance of this class now.
@Service
public class UserService {

	UserRepostory repostory;

	PasswordEncoder passwordEncoder;

	public UserService(UserRepostory repostory, PasswordEncoder passwordEncoder) {
		super();
		this.repostory = repostory;
		this.passwordEncoder = passwordEncoder;
	}

	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repostory.save(user);
	}

	public Page<User> getUsers(User loggedInUser, Pageable pageable) {
		if(loggedInUser!= null) {
			return repostory.findByUsernameNot(loggedInUser.getUsername(), pageable);
		}
		
		return repostory.findAll(pageable);
	}
}
