package com.coinpi.cn.financialAPI.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "security-management", url = "http://localhost:8082")
public interface ManagementSecurity {

	@GetMapping("/api/security/validate/{token}")
	ResponseEntity<?> tokenValidation(@PathVariable String token);
	
}
