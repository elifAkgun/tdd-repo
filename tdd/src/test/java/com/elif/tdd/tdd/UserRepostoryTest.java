package com.elif.tdd.tdd;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.elif.tdd.tdd.user.User;
import com.elif.tdd.tdd.user.UserRepostory;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepostoryTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired 
	UserRepostory userRepostory;
	
	@Test
	public void findByUserName_whenUserExists_returnUser() {
		User user = new User();
		user.setDisplayName("test-display-name");
		user.setPassword("passUser");
		user.setUsername("test-user-name");
		
		entityManager.persist(user);
		
		User userInDb = userRepostory.findByUsername("test-user-name");
		assertThat(userInDb).isNotNull();
	}

	@Test
	public void findByUserName_whenUserNotExists_returnNull() {
		
		User userInDb = userRepostory.findByUsername("test-user-name");
		assertThat(userInDb).isNull();
	}

}
