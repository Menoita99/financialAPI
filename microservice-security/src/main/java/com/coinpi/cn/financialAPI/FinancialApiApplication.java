package com.coinpi.cn.financialAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FinancialApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialApiApplication.class, args);
	}

}
