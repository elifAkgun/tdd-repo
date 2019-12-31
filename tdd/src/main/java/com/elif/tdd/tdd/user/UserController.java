package com.elif.tdd.tdd.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elif.tdd.tdd.shared.GenericResponseObject;

@RestController
public class UserController {
	
	@Autowired
	UserService service;

	@PostMapping("/v1/api/users")
	GenericResponseObject createUser(@RequestBody User user) {
		service.save(user);
		return new GenericResponseObject("Saved Successfully.");
	}

}
