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

import com.coinpi.cn.financialAPI.model.CreditCardModel;
import com.coinpi.cn.financialAPI.model.ExampleModel;
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
}
