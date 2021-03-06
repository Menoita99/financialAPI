package com.coinpi.cn.financialAPI.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinpi.cn.financialAPI.database.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>{

}
