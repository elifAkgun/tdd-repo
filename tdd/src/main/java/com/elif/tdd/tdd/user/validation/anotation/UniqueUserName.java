package com.elif.tdd.tdd.user.validation.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.elif.tdd.tdd.user.validation.UniqueUserNameValidator;

@Constraint (validatedBy = UniqueUserNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserName {
	
	String message() default "{tdd.constraints.username.UniqueUserName.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
