package com.elif.tdd.tdd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.h2.command.ddl.CreateView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.elif.tdd.tdd.error.ApiError;
import com.elif.tdd.tdd.shared.GenericResponseObject;
import com.elif.tdd.tdd.user.User;
import com.elif.tdd.tdd.user.UserRepostory;

@RunWith(value = SpringRunner.class) // having spring class on our test class.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // web server will be initialize
@ActiveProfiles("test") // During the test we have to use test profile
public class UserControllerTest {

	private static final String V1_API_USERS = "/v1/api/users";
	// we need http client. spring create application context for us.
	@Autowired // for field injection
	TestRestTemplate testRestTemplate;

	@Autowired // for field injection
	UserRepostory userRepostory;

	
	@BeforeEach
	public void cleanUp() {

		userRepostory.deleteAll();
	}

	// test methods methodname_condition_expectedbehaviour
	@Test
	public void postUser_whenUserIsValid_reciveOk() {
		User user = crateValidUser();
		// this is mock URL, user and object type for response type
		ResponseEntity<Object> response = postSignUp(user, Object.class);
		assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
	}

	@Test
	public void postUser_whenUserIsValid_userSavedToDatabase() {
		User user = crateValidUser();
		// this is mock URL, user and object type for response type
		postSignUp(user, Object.class);
		assertThat(1).isEqualTo(userRepostory.count());
	}

	@Test
	public void postUser_whenUserIsValid_reciveSuccessMessage() {
		User user = crateValidUser();

		// this is mock url, user and object type for response type
		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
		assertThat(response.getBody().getMessage()).isNotNull();
	}

	@Test
	public void postUser_whenUserIsValid_paswordIsHasheedInDb() {
		User user = crateValidUser();
		// this is mock URL, user and object type for response type
		postSignUp(user, GenericResponseObject.class);
		List<User> users = userRepostory.findAll();
		User inDb = users.get(0);

		assertThat(inDb.getPassword()).isNotEqualTo(user.getPassword());
	}

	@Test
	public void postUser_whenUserHasNullName_recieveBadRequest() {
		User user = crateValidUser();
		user.setUserName(null);
		// this is mock URL, user and object type for response type
		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());

	}

	@Test
	public void postUser_whenDisplayNameHasNull_recieveBadRequest() {
		User user = crateValidUser();
		user.setDisplayName(null);
		// this is mock URL, user and object type for response type
		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());

	}

	@Test
	public void postUser_whenPasswordHasNull_recieveBadRequest() {
		User user = crateValidUser();
		user.setPassword(null);
		// this is mock URL, user and object type for response type
		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());

	}

	@Test
	public void postUser_whenUserNameLessThanRequired_recieveBadRequest() {
		User user = crateValidUser();
		user.setUserName("abc");
		// this is mock URL, user and object type for response type
		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());

	}

	@Test
	public void postUser_whenDisplayNameLessThanRequired_recieveBadRequest() {
		User user = crateValidUser();
		user.setDisplayName("abc");
		// this is mock URL, user and object type for response type
		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());

	}

	@Test
	public void postUser_whenPasswordLessThanRequired_recieveBadRequest() {
		User user = crateValidUser();
		user.setPassword("abc");
		// this is mock URL, user and object type for response type
		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());

	}

	@Test
	public void postUser_whenUserNamexceedsTheLenghtLimit_recieveBadRequest() {
		User user = crateValidUser();

		String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a")
				.collect(Collectors.joining());

		user.setUserName(valueOf256Chars);
		// this is mock URL, user and object type for response type
		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());

	}

	@Test
	public void postUser_whenDisplayNameExceedsTheLenghtLimit_recieveBadRequest() {
		User user = crateValidUser();
		String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a")
				.collect(Collectors.joining());
		user.setDisplayName(valueOf256Chars);
		// this is mock URL, user and object type for response type
		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());

	}

	@Test
	public void postUser_whenPasswordxceedsTheLenghtLimit_recieveBadRequest() {
		User user = crateValidUser();
		String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a")
				.collect(Collectors.joining());
		user.setPassword(valueOf256Chars);
		// this is mock URL, user and object type for response type
		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());

	}
	
//	@Test
//	public void postUser_whenPasswordAllCharIsLower_recieveBadRequest() {
//		User user = crateValidUser();
//		user.setPassword("alllowercase");
//		// this is mock URL, user and object type for response type
//		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
//		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());
//
//	}

//	@Test
//	public void postUser_whenPasswordAllCharIsUpper_recieveBadRequest() {
//		User user = crateValidUser();
//		user.setPassword("ALLCHARISUPPER");
//		// this is mock URL, user and object type for response type
//		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
//		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());
//
//	}
//	
//	@Test
//	public void postUser_whenHasNoNumber_recieveBadRequest() {
//		User user = crateValidUser();
//		user.setPassword("allCharIsAlphabetic");
//		// this is mock URL, user and object type for response type
//		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
//		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());
//
//	}
	
//	@Test
//	public void postUser_whenHasNoChar_recieveBadRequest() {
//		User user = crateValidUser();
//		user.setPassword("123455");
//		// this is mock URL, user and object type for response type
//		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
//		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());
//	}
//	
//	@Test
//	public void postUser_whenHasNoSpecialCharacter_recieveBadRequest() {
//		User user = crateValidUser();
//		user.setPassword("123455asdasASDAA");
//		// this is mock URL, user and object type for response type
//		ResponseEntity<GenericResponseObject> response = postSignUp(user, GenericResponseObject.class);
//		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());
//
//	}
	
	
	@Test
	public void postUser_whenUserIsInvalid_recieveApiError() {
		User user = new User();
		// this is mock URL, user and object type for response type
		ResponseEntity<ApiError> response = postSignUp(user, ApiError.class);
		assertThat(V1_API_USERS).isEqualTo(response.getBody().getUrl());
	}
	
	@Test
	public void postUser_whenUserIsInvalid_recieveApiErrorWithValidationError() {
		User user = new User();
		// this is mock URL, user and object type for response type
		ResponseEntity<ApiError> response = postSignUp(user, ApiError.class);
		assertThat(3).isEqualTo(response.getBody().getValidationErrors().size());
	}
	
	@Test
	public void postUser_whenUserIsNullUserName_recieveMessageForNullUserName() {
		User user = crateValidUser();
		user.setUserName(null);
	
		// this is mock URL, user and object type for response type
		ResponseEntity<ApiError> response = postSignUp(user, ApiError.class);
		Map<String, String> validationErrors = response.getBody().getValidationErrors();
		
		assertThat("UserName cannot be null").isEqualTo(validationErrors.get("userName"));
	}
	
	@Test
	public void postUser_whenUserIsNullPassword_recieveGenericMessageForNullPassword() {
		User user = crateValidUser();
		user.setPassword(null);
	
		// this is mock URL, user and object type for response type
		ResponseEntity<ApiError> response = postSignUp(user, ApiError.class);
		Map<String, String> validationErrors = response.getBody().getValidationErrors();
		
		assertThat("Cannot be null").isEqualTo(validationErrors.get("password"));
	}
	
	@Test
	public void postUser_whenUserHasInvalidLengthUserName_recieveGenericMessageForLength() {
		User user = crateValidUser();
		user.setUserName("abc");
	
		// this is mock URL, user and object type for response type
		ResponseEntity<ApiError> response = postSignUp(user, ApiError.class);
		Map<String, String> validationErrors = response.getBody().getValidationErrors();
		
		assertThat("It must be 4 characters minimum and 255 characters maximum").isEqualTo(validationErrors.get("userName"));
	}
	
	@Test
	public void postUser_whenAnotherUserHasSameName_recieveBadRequest() {
		userRepostory.save(crateValidUser());
		
		User user = crateValidUser();
		ResponseEntity<Object> response = postSignUp(user, Object.class);
		
		assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());
	}
	
	@Test
	public void postUser_whenAnotherUserHasSameName_recieveMessageOfDuplicateUserName() {
		userRepostory.save(crateValidUser());
		
		User user = crateValidUser();
		
		ResponseEntity<ApiError> response = postSignUp(user, ApiError.class);
		Map<String, String> validationErrors = response.getBody().getValidationErrors();
		
		assertThat("This name is in use").isEqualTo(validationErrors.get("userName"));
	}
	
	public <T> ResponseEntity<T> postSignUp(Object request, Class<T> response) {
		return testRestTemplate.postForEntity(V1_API_USERS, request, response);
	}

	private User crateValidUser() {
		User user = new User();
		user.setUserName("test-user");
		user.setDisplayName("test-display");
		user.setPassword("asAS1234");
		return user;
	}

}
