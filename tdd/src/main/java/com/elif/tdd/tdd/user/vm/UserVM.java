package com.elif.tdd.tdd.user.vm;

import com.elif.tdd.tdd.user.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVM {

	private long id;
	
	private String username;
	
	private String displayName;
	
	private String image;
	
	public UserVM(User user) {
		this.setId(id);
		this.setUsername(username);
		this.setDisplayName(displayName);
		this.setImage(image);
		
	}
	
}
