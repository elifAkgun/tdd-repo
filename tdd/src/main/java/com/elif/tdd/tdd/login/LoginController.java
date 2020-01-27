package com.elif.tdd.tdd.login;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elif.tdd.tdd.shared.CurrentUser;
import com.elif.tdd.tdd.user.User;
import com.elif.tdd.tdd.user.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class LoginController {
	
	//first approach without annotation
//	@PostMapping("/v1/api/login")
//	public Map<String, Object> handleLogin() {
//		User loggedInUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
//		return Collections.singletonMap("id", loggedInUser.getId());
//	}
	
	@PostMapping("/v1/api/login")
	@JsonView(Views.Base.class)
	public User handleLogin(@CurrentUser User loggedInUser) {
		return loggedInUser;
	}
	
//	@ExceptionHandler({AccessDeniedException.class})
//	@ResponseStatus(HttpStatus.UNAUTHORIZED)
//	ApiError handleValidationException() {
//		
//		ApiError apiError = new ApiError(401, "Access Error", "/v1/api/login");
//		
//		return apiError;
//	}

}
