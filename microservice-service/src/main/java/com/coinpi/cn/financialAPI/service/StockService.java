package com.coinpi.cn.financialAPI.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;
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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class StockService {

	public static void main(String[] args) {
		StockService service = new StockService();
		service.sendHttpPostRequest("APPLE", 0, "POST");
	}

	@Autowired
	private ManagementServiceClient managementService;

	private String pythonService = "api/python";
	private String[] pythonEndpoints = { "/stock", "/top", "/news" };

	public StockPredictionModel getPredictionByStock(String stock) {

		ResponseEntity<?> response = managementService.subtractCall(JwtUtil.getUser().getId());
		System.out.println("subtracted");
		if (response.getStatusCode() == HttpStatus.OK)
			if ((boolean) response.getBody()) {
				sendHttpPostRequest(stock, 0, "POST");
				// TODO: comunicar com o data science
				// temporário, devolver este random
				StockPredictionModel model = getStock(stock);
				return model;
			}

		throw new IllegalStateException("not enough calls");
	}

	private String sendHttpPostRequest(String message, int endpoint, String requestType) {
		String json = "{\"data\": " + "\"" + message + "\"}";

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, json);
		Request request = new Request.Builder().url("http://127.0.0.1:5000/" + pythonService + pythonEndpoints[endpoint]).method(requestType, body)
				.addHeader("Content-Type", "application/json").build();
		try {
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
			return "Internal error";
		}

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
