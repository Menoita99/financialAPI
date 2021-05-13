package com.coinpi.cn.financialAPI.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.coinpi.cn.financialAPI.model.Action;
import com.coinpi.cn.financialAPI.model.StockPredictionModel;
import com.coinpi.cn.financialAPI.security.jwt.JwtUtil;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class StockService {

	@Autowired
	private ManagementServiceClient managementService;

	private String pythonService = "api/python";
	private String[] pythonEndpoints = { "/stock", "/top", "/news" };
	
	@Value("${python.ip}")
	private String pythonIp;
	@Value("${python.port}")
	private int pythonPort;
	
	public StockPredictionModel getPredictionByStock(String stock) {

		ResponseEntity<?> response = managementService.subtractCall(JwtUtil.getUser().getId());
		System.out.println("subtracted");
		if (response.getStatusCode() == HttpStatus.OK)
			if ((boolean) response.getBody()) {
				String predictionResponse = sendHttpRequest(stock, 0, "POST");
				StockPredictionModel model = getStock2(stock, predictionResponse);
				System.out.println(model);
				return model;
			}

		throw new IllegalStateException("not enough calls");
	}

	private String sendHttpRequest(String message, int endpoint, String requestType) {
		String json = "{\"data\": " + "\"" + message + "\"}";

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, json);
		Request request;
		if(requestType == "POST") {
			request = new Request.Builder().url("http://" + pythonIp + ":" + pythonPort + "/" + pythonService + pythonEndpoints[endpoint]).method("POST", body)
					.addHeader("Content-Type", "application/json").build();
		}else {
			request = new Request.Builder().url("http://" + pythonIp + ":" + pythonPort + "/" + pythonService + pythonEndpoints[endpoint]).method("GET", null)
					.addHeader("Content-Type", "application/json").build();
		}
		try {
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
			return "Internal error";
		}

	}
	
	private StockPredictionModel getStock2(String stock, String predResponse) {
		System.out.println(stock);
		System.out.println(predResponse);
		StockPredictionModel model = new StockPredictionModel();
		model.setDateTime(LocalDateTime.now());
		model.setStock(stock);
		double [] preds = new double[2];
		preds[0] = Double.parseDouble(predResponse.split(",")[0].replace("[", ""));
		preds[1] = Double.parseDouble(predResponse.split(",")[1].replace("]", ""));
		if (preds[0] > preds[1]) {
			model.setProb(preds[1]/preds[0]);
			model.setAction(Action.SELL);
		}
		else {
			model.setProb(preds[0]/preds[1]);
			model.setAction(Action.BUY);
		}
		return model;
	}
	public List<StockPredictionModel> getTopPredictions() {

		ResponseEntity<?> response = managementService.subtractCall(JwtUtil.getUser().getId());
		System.out.println("Subtracted");
		Map<String, Double> pairs = new LinkedHashMap<>();
		if (response.getStatusCode() == HttpStatus.OK)
			if ((boolean) response.getBody()) {
				List<StockPredictionModel> predictions = new ArrayList<StockPredictionModel>();
				String predictionResponse = sendHttpRequest("", 1, "GET");
				for(String x : predictionResponse.split("],")) {
					x = x.replace("[", "").replace("]", "").replace("\"", "").replace(" ","").replace(".csv", "");
					String [] values = x.split(",");
					pairs.put(values[1], Double.parseDouble(values[0]));
				}
				pairs.forEach((k,v) -> {
					double value = Math.round(v);
					double prob = 0;
					if(value >  v)
						prob = value - v;
					else if(value < v)
						prob = v - value;
					else
						prob = 1;
					predictions.add(new StockPredictionModel(k, prob, Action.BUY, LocalDateTime.now()));
				});
				return predictions;
			}
		throw new IllegalStateException("Not enough calls");
	}

	public int getPredictionsFromNews(String news) {
		ResponseEntity<?> response = managementService.subtractCall(JwtUtil.getUser().getId());
		System.out.println("Subtracted");
		if (response.getStatusCode() == HttpStatus.OK)
			if ((boolean) response.getBody()) {
				String predictionResponse = sendHttpRequest(news, 2, "POST");
				

				return Integer.parseInt(predictionResponse);
			}
		throw new IllegalStateException("Not enough calls");
	}

}
