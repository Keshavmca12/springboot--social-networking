package com.social.networking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.networking.entity.User;

public interface UserRepository  extends JpaRepository<User, Integer> {
	
	public Optional<User> findByUserName(String userName);

}
