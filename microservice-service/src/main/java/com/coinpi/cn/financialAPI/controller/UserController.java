package com.coinpi.cn.financialAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinpi.cn.financialAPI.model.ExampleModel;
import com.coinpi.cn.financialAPI.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	private UserService service;

	
	@PostMapping("/confirmToken")
	public ResponseEntity<?> confirmToken(@RequestBody ExampleModel rtt) {
		return ResponseEntity.ok(rtt);
	}
	
	
	@GetMapping("/service")
	public ResponseEntity<String> getUsingService() {
		return ResponseEntity.ok(service.example());
	}
	
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/token/{token}")
	public ResponseEntity<ExampleModel> getClientByToken(@PathVariable String token) {
		return ResponseEntity.ok(new ExampleModel(token));
	}
	
	
	@Secured({ "ROLE_ADMIN", "ROLE_CLIENT"})
	@GetMapping("/id/{id}")
	public ResponseEntity<String> getClientById(@PathVariable long id) {
		return ResponseEntity.ok("Received: "+id);
	}
}
