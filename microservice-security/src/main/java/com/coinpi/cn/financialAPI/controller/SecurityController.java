package com.coinpi.cn.financialAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinpi.cn.financialAPI.database.entity.User;
import com.coinpi.cn.financialAPI.model.UserDetailsModel;
import com.coinpi.cn.financialAPI.security.jwt.JwtUtil;
import com.coinpi.cn.financialAPI.service.UserService;

@RestController
@RequestMapping("api/security")
public class SecurityController{
	
	@Autowired
	private UserService service;
	
	@GetMapping("/validateToken")
	public ResponseEntity<?> validateToken(String token) {
		if(!JwtUtil.isTokenValid(token)) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
		String login = JwtUtil.getLogin(token);
		UserDetails userDetails = service.loadUserByUsername(login);
		List<GrantedAuthority> authorities = JwtUtil.getRoles(token);
		UserDetailsModel userDetailsModel = new UserDetailsModel(userDetails, authorities);
		return ResponseEntity.ok(userDetailsModel);
	}

}
