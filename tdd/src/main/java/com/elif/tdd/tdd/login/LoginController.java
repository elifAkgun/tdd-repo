package com.elif.tdd.tdd.login;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elif.tdd.tdd.shared.CurrentUser;
import com.elif.tdd.tdd.user.User;
import com.elif.tdd.tdd.user.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class LoginController {
	
	@PostMapping("/v1/api/login")
	@JsonView(Views.Base.class)
	public User handleLogin(@CurrentUser User loggedInUser) {
		return loggedInUser;
	}
}
