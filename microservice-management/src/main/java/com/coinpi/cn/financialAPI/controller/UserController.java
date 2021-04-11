package com.coinpi.cn.financialAPI.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.coinpi.cn.financialAPI.security.jwt.JwtUtil;
import com.coinpi.cn.financialAPI.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	private UserService service;

	
	@PostMapping("/buy")
	@Secured({ "ROLE_ADMIN", "ROLE_CLIENT"})
	public ResponseEntity<?> confirmToken( @RequestBody CreditCardModel rtt) {
		User user = JwtUtil.getUser();
		user = service.addCalls(user.getId(), rtt.getAmount()/0.01);
		return ResponseEntity.ok(user.getCalls());
	}
	
	
	
	
	@GetMapping("/remainingCalls")
	public ResponseEntity<?> getRemainingCalls() {
		User user = JwtUtil.getUser();
		try{
			return ResponseEntity.ok(service.getUserRemainingCalls(user.getId()));
		}catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Warning suppressed TODO
	 * 
	 */
	@GetMapping("/all")
	public ResponseEntity<List<UserInfoModel>> getAllClients(){
		List<UserInfoModel> users = new LinkedList<>(); //
		try{
			for( User u : service.getAll()) 
				users.add(new UserInfoModel(u));
		 	return ResponseEntity.ok(users);
		}catch (Exception e) {
			return new ResponseEntity<List<UserInfoModel>>(HttpStatus.BAD_REQUEST);
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
	@PutMapping("/{id}")
	public ResponseEntity<?> putClientById(@PathVariable long id, @RequestBody UserInfoModel info) {
		try{
		 	return ResponseEntity.ok(new UserInfoModel(service.update(id,info)));
		}catch (Exception e) {
			return new ResponseEntity<List<UserInfoModel>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getClientById(@PathVariable long id) {
		try{
		 	return ResponseEntity.ok(new UserInfoModel(service.findById(id)));
		}catch (Exception e) {
			return new ResponseEntity<List<UserInfoModel>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//@Secured({"ROLE_MICROSERVICE"})
	@GetMapping("/subtractCall/{id}")
	public ResponseEntity<?> subtractCall(@PathVariable long id) {
		try{
			if(service.subtractCallsFrom(id))
				return ResponseEntity.ok("Call subtracted");
			else
				return new ResponseEntity<String>("Not enough calls", HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
