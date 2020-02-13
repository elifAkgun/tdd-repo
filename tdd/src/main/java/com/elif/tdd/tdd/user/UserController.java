package com.elif.tdd.tdd.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elif.tdd.tdd.error.ApiError;
import com.elif.tdd.tdd.shared.CurrentUser;
import com.elif.tdd.tdd.shared.GenericResponseObject;
import com.elif.tdd.tdd.user.vm.UserVM;

@RestController
@RequestMapping("/v1/api")
public class UserController {
	
	@Autowired
	UserService service;

	@PostMapping("/users")
	GenericResponseObject createUser(@Valid @RequestBody User user) {
		service.save(user);
		return new GenericResponseObject("Saved Successfully.");
	}
	
	@GetMapping("/users") 
	Page<UserVM> getUsers(@CurrentUser User loggedInUser,Pageable page)
	{
		return service.getUsers(loggedInUser,page).map(UserVM::new);
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ApiError handleValidationException(MethodArgumentNotValidException exception,
			HttpServletRequest request) {
		BindingResult bindingResult = exception.getBindingResult();
		
		Map<String, String> validationErrors = new HashMap<String, String>();
		
		for (FieldError fieldError: bindingResult.getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		ApiError apiError = new ApiError(400, "ValidationError", request.getServletPath());
		apiError.setValidationErrors(validationErrors);
		
		return apiError;
	}
	

}
