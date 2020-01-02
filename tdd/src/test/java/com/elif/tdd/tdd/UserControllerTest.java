package com.elif.tdd.tdd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.elif.tdd.tdd.shared.GenericResponseObject;
import com.elif.tdd.tdd.user.User;
import com.elif.tdd.tdd.user.UserRepostory;

@RunWith(value = SpringRunner.class) // having spring class on our test class.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // web server will be initialize
@ActiveProfiles("test") // During the test we have to use test profile
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
		// this is mock url, user and object type for response type
		ResponseEntity<Object> response = testRestTemplate.postForEntity(V1_API_USERS, user, Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void postUser_whenUserIsValid_userSavedToDatabase() {
		User user = crateValidUser();
		// this is mock url, user and object type for response type
		testRestTemplate.postForEntity(V1_API_USERS, user, Object.class);
		assertThat(userRepostory.count()).isEqualTo(1);
	}
	
	@Test
	public void postUser_whenUserIsValid_reciveSuccessMessage() {
		User user = crateValidUser();

		// this is mock url, user and object type for response type
		ResponseEntity<GenericResponseObject> response = testRestTemplate.postForEntity(V1_API_USERS, user, GenericResponseObject.class);
		assertThat(response.getBody().getMessage()).isNotNull();
	}
	
	@Test
	public void postUser_whenUserIsValid_paswordIsHasheedInDb() {
		User user = crateValidUser();
		// this is mock url, user and object type for response type
		testRestTemplate.postForEntity(V1_API_USERS, user, GenericResponseObject.class);
		List<User> users = userRepostory.findAll();
		User inDb = users.get(0);
		
		assertThat(inDb.getPassword()).isNotEqualTo(user.getPassword());
	}

	private User crateValidUser() {
		User user = new User();
		user.setUserName("test-user");
		user.setDisplayName("test-display");
		user.setPassword("P*ssword");
		return user;
	}

}
