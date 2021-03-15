package com.coinpi.cn.financialAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardModel {
	private String cardNumber;
	private int cvc;
	private String validDate;
	private double amount;
	private Currency currency;
}