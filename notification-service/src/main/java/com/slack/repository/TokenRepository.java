package com.slack.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.slack.model.Token;
import com.slack.model.User;

@Repository
public interface TokenRepository extends MongoRepository<Token, Integer>{
	
	public Token findByTokenString(String tokenString);

	public User getUserByTokenString(String tokenString);



}
