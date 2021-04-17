/*package com.coinpi.cn.financialAPI.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "management-service", url = "http://localhost:8081")
public interface ManagementSecurity {

	@GetMapping("security/api/security/validateToken")
	ResponseEntity<?> tokenValidation(@RequestParam String token);
	
} */
