package com.coinpi.cn.financialAPI.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.coinpi.cn.financialAPI.model.Action;
import com.coinpi.cn.financialAPI.model.StockPredictionModel;
import com.coinpi.cn.financialAPI.security.jwt.JwtUtil;

@Service
public class StockService {

	@Autowired
	private ManagementServiceClient managementService;

	public StockPredictionModel getPredictionByStock(String stock) {

		ResponseEntity<?> response = managementService.subtractCall(JwtUtil.getUser().getId());
		System.out.println("subtracted");
		if (response.getStatusCode() == HttpStatus.OK)
			if ((boolean) response.getBody()) {
				
				
				
				
				
				// TODO: comunicar com o data science
				// temporário, devolver este random
				StockPredictionModel model = getStock(stock);
				return model;
			}

		throw new IllegalStateException("not enough calls");
	}

	private StockPredictionModel getStock(String stock) {
		Random r = new Random();
		double prob = r.nextDouble();
		StockPredictionModel model = new StockPredictionModel();
		model.setDateTime(LocalDateTime.now());
		model.setStock(stock);
		model.setProb(prob);
		if (prob < 0.5)
			model.setAction(Action.SELL);
		else
			model.setAction(Action.BUY);
		return model;
	}

	public List<StockPredictionModel> getTopPredictions() {
		ResponseEntity<?> response = managementService.subtractCall(JwtUtil.getUser().getId());
		System.out.println("Subtracted");
		if (response.getStatusCode() == HttpStatus.OK)
			if ((boolean) response.getBody()) {
				List<StockPredictionModel> predictions = new ArrayList<StockPredictionModel>();
				// TODO: Comunicar com o python para receber as predictions
				// temporário, devolve uma lista de random strings

				Random r = new Random();
				String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

				for (int i = 0; i < 10; i++) {
					String stockName = "";
					for (int j = 0; j < 3; j++) {
						stockName += CHARS.charAt(r.nextInt(CHARS.length()));
					}
					
					StockPredictionModel model = getStock(stockName);
					predictions.add(model);
				}
				return predictions;
			}
		throw new IllegalStateException("Not enough calls");
	}

	public List<StockPredictionModel> getPredictionsFromNews() {
		ResponseEntity<?> response = managementService.subtractCall(JwtUtil.getUser().getId());
		System.out.println("Subtracted");
		if (response.getStatusCode() == HttpStatus.OK)
			if ((boolean) response.getBody()) {
				List<StockPredictionModel> predictions = new ArrayList<StockPredictionModel>();
				// TODO: Comunicar com o python para receber as predictions
				// temporário, devolve uma lista de random strings

				Random r = new Random();
				String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

				for (int i = 0; i < r.nextInt(5); i++) {
					String stockName = "";
					for (int j = 0; j < 3; j++) {
						stockName += CHARS.charAt(r.nextInt(CHARS.length()));
					}
					
					StockPredictionModel model = getStock(stockName);
					predictions.add(model);
				}
				return predictions;
			}
		throw new IllegalStateException("Not enough calls");
	}

}
