package com.slack.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.lang.Nullable;
import com.slack.model.User;
@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
	
	@Nullable
	public User findByUserMail(String userMail);
	
	public List<User> findByFirstNameOrLastName(String name);
	
	public User findByUserId(int userId);
}
