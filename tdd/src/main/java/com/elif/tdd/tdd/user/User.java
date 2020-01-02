package com.elif.tdd.tdd.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

 // this annotation generated getter, setter, constructor, equals and hash code
		// methods
@Data

//this is an entity represents a database table
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String userName;
	private String displayName;
	private String password;

}
