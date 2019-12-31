package com.elif.tdd.tdd.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@PostMapping("/v1/api/users")
	void createUser() {
	}

}
