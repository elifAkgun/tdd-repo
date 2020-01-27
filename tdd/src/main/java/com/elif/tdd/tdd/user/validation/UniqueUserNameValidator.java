package com.elif.tdd.tdd.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.elif.tdd.tdd.user.User;
import com.elif.tdd.tdd.user.UserRepostory;
import com.elif.tdd.tdd.user.validation.anotation.UniqueUserName;

//First parameter is our annotation, second one is our field which will be validated(username)
public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String>{

	@Autowired
	UserRepostory userRepostory;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		User inDbUser = userRepostory.findByUsername(value);
		
		if(inDbUser==null) {
			return true;
		}
		
		return false;
	}

}
