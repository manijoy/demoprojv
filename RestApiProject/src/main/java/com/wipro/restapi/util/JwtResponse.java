package com.wipro.restapi.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class JwtResponse {
	String jsonString;


	public JwtResponse(String jsonString) {
		super();
		this.jsonString = jsonString;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	
}
