package com.coinpi.cn.financialAPI.database.entity;

import org.springframework.security.core.GrantedAuthority;

public enum AcessRole {
	
	ROLE_ADMIN,ROLE_CLIENT,ROLE_TESTE,ROLE_SISTEM;
	
	public static GrantedAuthority toGrantedAuthority(AcessRole r) {
		return new GrantedAuthority() { 

			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return r.toString();
			}
		};
	}
}