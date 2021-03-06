package com.coinpi.cn.financialAPI.database.entity;



import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import lombok.Data;
import lombok.NoArgsConstructor;

@Table(indexes = {
		@Index(name = "stock_date_index", columnList = "stock, date", unique = true),
		@Index(name = "stock_index", columnList = "stock")
})
@Entity
@Data
@NoArgsConstructor
public class Stock {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "This field can't be empty")
	@Column(nullable = false)
	private String stock;
	private LocalDate date;
	private double open;
	private double high;
	private double low;
	private double close;
	private double adjClose;
	private int volume;
	

}
