package com.coinpi.cn.financialAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coinpi.cn.financialAPI.database.entity.User;
import com.coinpi.cn.financialAPI.security.jwt.JwtUtil;
import com.coinpi.cn.financialAPI.service.UserService;

@RestController
@RequestMapping(produces = "application/json" , value = "/security")
public class SecurityController{
	
	@Autowired
	private UserService service;
	
	@GetMapping("/validateToken")
	public ResponseEntity<?> validateToken(@RequestParam String token) {
		try {
		if(!JwtUtil.isTokenValid(token)) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
		String login = JwtUtil.getLogin(token);
		User user = service.findByUsername(login);
		user.getLogs().clear();
		
//		ObjectMapper mapper = new ObjectMapper();
//		String str = mapper.writeValueAsString(user).replace("\\", "\\\\");
		return ResponseEntity.ok(user);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
