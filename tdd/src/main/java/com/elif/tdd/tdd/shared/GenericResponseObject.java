package com.elif.tdd.tdd.shared;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericResponseObject {

	private String message;

	public GenericResponseObject(String message) {
		this.message = message;
	}

}
