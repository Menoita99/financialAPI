package com.coinpi.cn.financialAPI.model;

import com.coinpi.cn.financialAPI.database.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoModel {
	
	private String email;
	private String firstName;
	private String lastName;
	private long calls;
	
	public UserInfoModel(User user) {
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.calls = user.getCalls();
	}
}
