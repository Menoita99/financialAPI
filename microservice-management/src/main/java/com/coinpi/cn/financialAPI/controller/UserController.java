package com.coinpi.cn.financialAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinpi.cn.financialAPI.database.entity.User;
import com.coinpi.cn.financialAPI.model.CreditCardModel;
import com.coinpi.cn.financialAPI.model.UserInfoModel;
import com.coinpi.cn.financialAPI.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	private UserService service;

	
	@PostMapping("/buy")
	@Secured({ "ROLE_ADMIN", "ROLE_CLIENT"})
	public ResponseEntity<?> confirmToken(@RequestBody CreditCardModel rtt) {
		return ResponseEntity.ok(rtt);
	}
	
	@Secured({ "ROLE_CLIENT" })
	@GetMapping("/remainingCalls")
	public ResponseEntity<?> getRemainingCalls() {
		//TODO: validade user authentication
		int id = 0; //
		try{
			int remainingCalls = service.getUserRemainingCalls(id);
			return ResponseEntity.ok(remainingCalls);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
	}
	
	/**
	 * Warning suppressed TODO
	 * 
	 */
	@SuppressWarnings("null")
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/all")
	public ResponseEntity<List<UserInfoModel>> getAllClients(){
		List<UserInfoModel> users = null; //
		try{
			for( User u : service.getAll()) {
				users.add(new UserInfoModel(u));
			}
		 	return ResponseEntity.ok(users);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<List<UserInfoModel>>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	/**
	 * doesnt return anything, aka ok
	 * recieves a user 
	 * shouldnt update him here 
	 * sends the user up so we can update him in the specific place
	 * TODO - Check for implementation
	 */
	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/user/{id}")
	public ResponseEntity<?> putClientById(@PathVariable User user) {
		UserDetails updatedUser = service.loadUserByUsername(user.getEmail());		
		return ResponseEntity.ok("Updated user");
	}
	
	
}
