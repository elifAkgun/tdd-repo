package com.elif.tdd.tdd.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.elif.tdd.tdd.user.validation.anotation.UniqueUserName;

import lombok.Data;

 // this annotation generated getter, setter, constructor, equals and hash code
		// methods
@Data
//this is an entity represents a database table
@Entity
//We can use this anotation to solve unique username issue.
//But it is effective if your db has access multiple application
//So we can solve this issue on application layer
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = "userName"))

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "{tdd.constraints.userName.notNull.message}")
	@Size(min = 4, max = 255)
	@UniqueUserName
	private String userName;
	
	@Size(min = 4,max = 255)
	@NotNull
	private String displayName;
	
	@Size(min = 4,max = 255)
	@NotNull
	private String password;

}
