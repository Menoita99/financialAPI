package com.coinpi.cn.financialAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistModel {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
}
