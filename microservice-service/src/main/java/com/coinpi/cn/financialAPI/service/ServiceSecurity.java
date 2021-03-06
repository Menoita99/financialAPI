package com.coinpi.cn.financialAPI.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "security-service", url="http://35.244.221.157")
public interface ServiceSecurity {
	
	@GetMapping("/security/validateToken")
	ResponseEntity<String> tokenValidation(@RequestParam String token);
}

