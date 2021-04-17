package com.coinpi.cn.financialAPI.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller {

	@GetMapping
	public ResponseEntity<String> healthyCheck() {
		return ResponseEntity.ok("Healthy! "+LocalDateTime.now());
	}
}
