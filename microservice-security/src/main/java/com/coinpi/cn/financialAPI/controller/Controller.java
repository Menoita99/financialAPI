package com.coinpi.cn.financialAPI.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinpi.cn.financialAPI.database.entity.User;
import com.coinpi.cn.financialAPI.database.repository.UserRepository;
import com.coinpi.cn.financialAPI.model.RegistModel;

@RestController
@RequestMapping
public class Controller {
	
	@Autowired
	private UserRepository userRepo;
	

	@GetMapping("/")
	public ResponseEntity<String> healthyCheck() {
		return ResponseEntity.ok("Healthy! "+LocalDateTime.now());
	}
	
	@GetMapping("/loaderio-ee3e2e35dd64ed6093114831839a2929/")//, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	@ResponseBody
	public ResponseEntity<String> testCheck() throws IOException {
	//	return new FileSystemResource("src/main/resources/loaderio-ee3e2e35dd64ed6093114831839a2929.txt"); 
		return ResponseEntity.ok("loaderio-ee3e2e35dd64ed6093114831839a2929");
	}


	@PostMapping("/security/register")
	public ResponseEntity<String> confirmToken(@RequestBody RegistModel registData) {
		try {
			User user = userRepo.save(new User(registData));
			return ResponseEntity.ok("User "+user.getUsername()+ " created with success");
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
}
