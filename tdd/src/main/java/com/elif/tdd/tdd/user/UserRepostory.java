package com.elif.tdd.tdd.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//This interface responsible for db operations.
//this interface extends from jpa repository. first parameter is entity second parameter is id type.
//when we run our application spring will create proxy implementation based on this interface
public interface UserRepostory extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	
	Page<User> findByUsernameNot(String username, Pageable pageable); 
	

}
