package com.coinpi.cn.financialAPI.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsModel {
	
	private UserDetails userDetails;
	private List<GrantedAuthority> authorities;

}
