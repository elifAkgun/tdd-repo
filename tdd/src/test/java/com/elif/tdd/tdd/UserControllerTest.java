package com.elif.tdd.tdd;


import static org.assertj.core.api.Assertions.*;

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

import com.elif.tdd.tdd.user.User;

@RunWith(value = SpringRunner.class) // having spring class on our test class.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // web server will be initialize
@ActiveProfiles("test") // During the test we have to use test profile
public class UserControllerTest {

	//we need http client. spring create application context for us.
	@Autowired//for field injection
	TestRestTemplate testRestTemplate;

	// test methods methodname_condition_expectedbehaviour
	@Test
	public void postUser_whenUserIsValid_reciveOk() {
		User user = new User();
		user.setUserName("test-user");
		user.setDisplayName("test-display");
		user.setPassword("P*ssword");
		
		//this is mock url, user and object type for response type 
		ResponseEntity<Object> response = testRestTemplate.postForEntity("/v1/api/users", user, Object.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
