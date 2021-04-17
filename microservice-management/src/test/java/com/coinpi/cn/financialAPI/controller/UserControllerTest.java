package com.coinpi.cn.financialAPI.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.coinpi.cn.financialAPI.model.CreditCardModel;
import com.coinpi.cn.financialAPI.model.Currency;
import com.coinpi.cn.financialAPI.service.UserService;

public class UserControllerTest {
	
	UserService userService = Mockito.mock(UserService.class);
	UserController controller = new UserController(userService);
	
	@Test
	void buyCallsTest(){
		CreditCardModel cc = new CreditCardModel("1234 4654 1231 5463", 123, "02/25", 53.0, Currency.EUR);
		ResponseEntity<?> response = controller.buyCalls(cc);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void getRemainingCallsTest(){
		ResponseEntity<?> response = controller.getRemainingCalls();
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void getAllClientsTest(){
		ResponseEntity<?> response = controller.getAllClients();
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void getClientByIdTest(){
		ResponseEntity<?> response = controller.getClientById(1);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void subtractCall(){
		ResponseEntity<?> response = controller.subtractCall(1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	

}
