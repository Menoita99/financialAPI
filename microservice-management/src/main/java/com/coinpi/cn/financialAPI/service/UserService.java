package com.coinpi.cn.financialAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coinpi.cn.financialAPI.database.entity.User;
import com.coinpi.cn.financialAPI.database.repository.UserRepository;
import com.coinpi.cn.financialAPI.model.UserInfoModel;

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

	@Autowired
	private UserRepository userRepo;

	public String example() {
		return "Calling service Method";
	}

	/**
	 * Find user by username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if (user == null)
			throw new UsernameNotFoundException("User with email " + username + "  not found!");
		return user;
	}

	public long getUserRemainingCalls(long id) {
		return userRepo.getOne(id).getCalls();
	}

	/**
	 * 
	 * @return a list of all users
	 */
	public List<User> getAll() {
		return userRepo.findAll();
	}

	public User update(long id, UserInfoModel info) {
		User u = userRepo.getOne(id);
		u.update(info);
		return userRepo.save(u);
	}

	public User addCalls(long id, double calls) {
		User u = userRepo.getOne(id);
		u.setCalls(u.getCalls()+(long)calls);
		return userRepo.save(u);
	}
	
	public boolean subtractCallsFrom(long id) {
		System.out.println("Subtract was called on management service!!!");
		User u = userRepo.getOne(id);
		if(u.getCalls() >=1) {
			u.setCalls(u.getCalls()-1);
			userRepo.save(u);
			return true;
		}
		return false;
	}

	public User findById(long id) {
		return userRepo.getOne(id);
	}
}
