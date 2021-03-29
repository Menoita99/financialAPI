package com.coinpi.cn.financialAPI.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "management-service")
public interface ManagementServiceClient {
	
	@GetMapping("/subtractCall/{id}")
	ResponseEntity<?> subtractCall(@PathVariable long id);
	
	
}
