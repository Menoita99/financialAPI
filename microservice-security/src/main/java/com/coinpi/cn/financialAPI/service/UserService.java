package com.coinpi.cn.financialAPI.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.Data;

/**
 * 
 * User Business logic
 * 
 * @author rui.menoita
 */
@Data
@Service(value = "userService")
public class UserService implements UserDetailsService {

	
	
	public String example() {
		return "Calling service Method";
	}

	/**
	 * Find user by username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userRepo.findByEmail(username);
//		if (user == null)
//			throw new UsernameNotFoundException("User with email " + username + "  not found!");
//		return user;
		return null;
	}
}
