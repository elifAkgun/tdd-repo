package com.elif.tdd.tdd.user;

import org.springframework.data.jpa.repository.JpaRepository;

//This interface responsible for db operations.
//this interface extends from jpa repository. first parameter is entity second parameter is id type.
//when we run our application spring will create proxy implementation based on this interface
public interface UserRepostory extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	
//	//@Query("Select u from User u")
//	@Query(value = "select * from user", nativeQuery = true)
//	Page<UserProjection> findAllUsers(Pageable pageable);
}
