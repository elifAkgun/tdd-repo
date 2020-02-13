package com.elif.tdd.tdd;

import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.elif.tdd.tdd.user.User;
import com.elif.tdd.tdd.user.UserService;

//This class is exclueded because of junit tests. 
@SpringBootApplication
public class TddApplication {

	public static void main(String[] args) {
		SpringApplication.run(TddApplication.class, args);
	}

	@Bean
	@Profile("!test")
	CommandLineRunner run(UserService userService) {
		return (args) -> {
			IntStream.rangeClosed(1, 15).mapToObj(i -> {
				User user = new User();
				user.setDisplayName("display" + i);
				user.setUsername("user" + i);
				user.setPassword("asAS1234");
				return user;
			}).forEach(userService::save);
		};
	}
	

}
