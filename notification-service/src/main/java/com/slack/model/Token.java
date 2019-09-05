package com.slack.model;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


public class Token {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tokenId;
	private int userId;
	
	@Field("token-string")
	@Indexed(unique = true)
	private String tokenString;
	
	private int timeout;
	
	
	public int getTokenId() {
		return tokenId;
	}
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTokenString() {
		return tokenString;
	}
	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
