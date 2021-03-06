package com.coinpi.cn.financialAPI.service;

import java.util.ArrayList;
import java.util.List;

import com.coinpi.cn.financialAPI.model.StockPredictionModel;

public class StockService {
	

	public StockPredictionModel getPredictionByStock(String stock) {
		
		//TODO: Comunicar com o python para receber a prediction
		
		return null;
	}

	public List<StockPredictionModel> getTopPredictions() {
		List<StockPredictionModel> predictions = new ArrayList<StockPredictionModel>();
		//TODO: Comunicar com o python para receber as predictions
		
		return predictions;
	}

}
