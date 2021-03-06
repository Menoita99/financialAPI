package com.coinpi.cn.financialAPI.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinpi.cn.financialAPI.database.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
}
