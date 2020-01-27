package com.elif.tdd.tdd;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.elif.tdd.tdd.error.ApiError;
import com.elif.tdd.tdd.user.User;
import com.elif.tdd.tdd.user.UserRepostory;
import com.elif.tdd.tdd.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // web server will be initialize
@ActiveProfiles("test") // During the test we have to use test profile
public class LoginControllerTest {
	
	private static final String API_V1_LOGIN = "/v1/api/login";
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	UserRepostory userRepostory;
	
	@Autowired
	UserService userService;
	
	@BeforeEach
	public void cleanUp() {
		userRepostory.deleteAll();
		restTemplate.getRestTemplate().getInterceptors().clear();
	}
	
	@Test
	public void postLogin_withoutUserCredentials_recieveUnauthorized() {
		ResponseEntity<Object> login = login(Object.class);
		assertThat(HttpStatus.UNAUTHORIZED).isEqualTo(login.getStatusCode());
	}
	
	@Test
	public void postLogin_withIncorrectUserCredentials_recieveUnauthorized() {
		authenticate();
		ResponseEntity<Object> login = login(Object.class);
		assertThat(HttpStatus.UNAUTHORIZED).isEqualTo(login.getStatusCode());
	}

	
	@Test
	public void postLogin_withoutUserCredentials_recieveApiError() {
		ResponseEntity<ApiError> login = login(ApiError.class);
		assertThat(login.getBody().getUrl()).isEqualTo(API_V1_LOGIN);
	}
	

	@Test
	public void postLogin_withoutUserCredentials_ApiErrorWithoutValidationsError() {
		ResponseEntity<String> login = login(String.class);
		assertThat(login.getBody().contains("validationErrors")).isFalse();
	}
	
	@Test
	public void postLogin_withIncorrectUserCredentials_recieveUnauthorizedWithoutWWWAutheticationHeader() {
		ResponseEntity<Object> login = login(Object.class);
		assertThat(login.getHeaders().containsKey("WWW-Authenticate")).isFalse();
	}
	@Test
	public void postLogin_withValidCredentials_recieveOk() {
		userService.save(TestUtil.crateValidUser());
		authenticate();
		ResponseEntity<Object> responseEntity = login(Object.class);
		assertThat(HttpStatus.OK).isEqualTo(responseEntity.getStatusCode());
	}
	
	@Test
	public void postLogin_withValidCredentials_recieveLoggedInUserId() {
		User inDb = userService.save(TestUtil.crateValidUser());
		authenticate();
		ResponseEntity<Map<String, Object>> responseEntity = login(
				new ParameterizedTypeReference<Map<String, Object>>() {});
		Map<String, Object> body = responseEntity.getBody();
		
		Integer id = (Integer) body.get("id");
		assertThat(id).isEqualTo(inDb.getId());
	}
	
	@Test
	public void postLogin_withValidCredentials_recieveLoggedInUsersImage() {
		User inDb = userService.save(TestUtil.crateValidUser());
		authenticate();
		ResponseEntity<Map<String, Object>> responseEntity = login(
				new ParameterizedTypeReference<Map<String, Object>>() {});
		Map<String, Object> body = responseEntity.getBody();
		
		String image = (String) body.get("image");
		assertThat(image).isEqualTo(inDb.getImage());
	}
	
	@Test
	public void postLogin_withValidCredentials_recieveLoggedInUsersDisplayName() {
		User inDb = userService.save(TestUtil.crateValidUser());
		authenticate();
		ResponseEntity<Map<String, Object>> responseEntity = login(
				new ParameterizedTypeReference<Map<String, Object>>() {});
		Map<String, Object> body = responseEntity.getBody();
		
		String displayName = (String) body.get("displayName");
		assertThat(displayName).isEqualTo(inDb.getDisplayName());
	}
	@Test
	public void postLogin_withValidCredentials_notRecieveLoggedInUsersPassword() {
		userService.save(TestUtil.crateValidUser());
		authenticate();
		ResponseEntity<Map<String, Object>> responseEntity = login(
				new ParameterizedTypeReference<Map<String, Object>>() {});
		Map<String, Object> body = responseEntity.getBody();
		
		assertThat(body.containsKey("password")).isFalse();
	}

	private void authenticate() {
		restTemplate.getRestTemplate().
			getInterceptors().add(new BasicAuthenticationInterceptor("test-user", "asAS1234"));
	}

	public <T> ResponseEntity<T> login(Class<T> responseType ){
		return restTemplate.postForEntity(API_V1_LOGIN, null, responseType);
	}

	public <T> ResponseEntity<T> login(ParameterizedTypeReference<T> responseType){
		return restTemplate.exchange(API_V1_LOGIN, HttpMethod.POST,null, responseType);
	}
}
