package com.coinpi.cn.financialAPI.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockPredictionModel {

	private String stock;
	private double prob;
	private Action action;
	private LocalDateTime dateTime;	
	
}
