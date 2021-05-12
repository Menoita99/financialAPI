package com.coinpi.cn.financialAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coinpi.cn.financialAPI.model.StockPredictionModel;
import com.coinpi.cn.financialAPI.service.StockService;

@RestController
@RequestMapping("service/api/stock")
public class StockController {
	
	@Autowired
	private StockService service;
	

	
	@Secured({ "ROLE_ADMIN", "ROLE_CLIENT"})
	@GetMapping("/{stock}")
	public ResponseEntity<?> getPredictionByStock(@PathVariable String stock) {
		try {
			StockPredictionModel stockPrediction = service.getPredictionByStock(stock);
			return ResponseEntity.<StockPredictionModel>ok(stockPrediction);			
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//@Secured({ "ROLE_ADMIN", "ROLE_CLIENT"})
	@GetMapping("/top")
	public ResponseEntity<?> getTopPredictions() {
		try {
			List<StockPredictionModel> stockPredictions = service.getTopPredictions();
			return ResponseEntity.<List<StockPredictionModel>>ok(stockPredictions);			
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@Secured({ "ROLE_ADMIN", "ROLE_CLIENT"})
	@GetMapping("/news")
	public ResponseEntity<?> getNewImpact(@RequestParam String news) {
		try {
			int stockPredictions = service.getPredictionsFromNews(news);
			if(stockPredictions == 1)
				return ResponseEntity.<String>ok("This new will most likely make the market go up!");
			else
				return ResponseEntity.<String>ok("This new will most likely not make the market go up!");		
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
