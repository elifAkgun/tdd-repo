package com.elif.tdd.tdd.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.elif.tdd.tdd.user.User;
import com.elif.tdd.tdd.user.UserRepostory;

@Service
public class AuthUserService implements UserDetailsService{

	@Autowired
	UserRepostory userRepostory;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepostory.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User Not Found!");
		}
		return user;
	}

}
