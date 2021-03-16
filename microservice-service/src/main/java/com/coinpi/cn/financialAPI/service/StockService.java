package com.coinpi.cn.financialAPI.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.coinpi.cn.financialAPI.model.Action;
import com.coinpi.cn.financialAPI.model.StockPredictionModel;

@Service
public class StockService {
	

	public StockPredictionModel getPredictionByStock(String stock) {
		
		//TODO: comunicar com o data science
		//temporário, devolver este random
		Random r = new Random();
		double prob = r.nextDouble();
		StockPredictionModel model = new StockPredictionModel();
		model.setDateTime(LocalDateTime.now());
		model.setStock(stock);
		model.setProb(prob);
		if(prob < 0.5)
			model.setAction(Action.SELL);
		else
			model.setAction(Action.BUY);
		return model;
	}

	public List<StockPredictionModel> getTopPredictions() {
		List<StockPredictionModel> predictions = new ArrayList<StockPredictionModel>();
		//TODO: Comunicar com o python para receber as predictions
		//temporário, devolve uma lista de random strings
		
		Random r = new Random();
		String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		for(int i = 0; i < 10; i++) {
			String stockName = "";
			for (int j = 0; j < 3; j++) {
				stockName += CHARS.charAt(r.nextInt(CHARS.length()));
			}
			StockPredictionModel model = getPredictionByStock(stockName);
			predictions.add(model);
		}
		return predictions;
	}

	public List<StockPredictionModel> getPredictionsFromNews() {
		
		List<StockPredictionModel> predictions = new ArrayList<StockPredictionModel>();
		//TODO: Comunicar com o python para receber as predictions
		//temporário, devolve uma lista de random strings
		
		Random r = new Random();
		String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		for(int i = 0; i < r.nextInt(5); i++) {
			String stockName = "";
			for (int j = 0; j < 3; j++) {
				stockName += CHARS.charAt(r.nextInt(CHARS.length()));
			}
			StockPredictionModel model = getPredictionByStock(stockName);
			predictions.add(model);
		}
		return predictions;
	}

}
