package com.coinpi.cn.financialAPI.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControllerTest {
	
	@Test
	void healthyCheck() {
		Controller controller = new Controller();
		ResponseEntity<String> response = controller.healthyCheck();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}
