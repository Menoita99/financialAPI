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
@RequestMapping("api/stock")
public class StockController {
	
	@Autowired
	private StockService service;
	

	
	//@Secured({ "ROLE_ADMIN", "ROLE_CLIENT"})
	@GetMapping("/{stock}")
	public ResponseEntity<StockPredictionModel> getPredictionByStock(@PathVariable String stock) {
		try {
			StockPredictionModel stockPrediction = service.getPredictionByStock(stock);
			return ResponseEntity.<StockPredictionModel>ok(stockPrediction);			
		}catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	//@Secured({ "ROLE_ADMIN", "ROLE_CLIENT"})
	@GetMapping("/top")
	public ResponseEntity<List<StockPredictionModel>> getTopPredictions() {
		try {
			List<StockPredictionModel> stockPredictions = service.getTopPredictions();
			return ResponseEntity.<List<StockPredictionModel>>ok(stockPredictions);			
		}catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@Secured({ "ROLE_ADMIN", "ROLE_CLIENT"})
	@GetMapping("/news")
	public ResponseEntity<?> getNewImpact(@RequestParam String news) {
		try {
			List<StockPredictionModel> stockPredictions = service.getPredictionsFromNews();
			System.out.println(news);
			if(stockPredictions.isEmpty())
				return ResponseEntity.<String>ok("This new will most likely not influenciate anything.");
			else
				return ResponseEntity.<List<StockPredictionModel>>ok(stockPredictions);			
		}catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
