package com.example.stockservice.repository;

import com.example.stockservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findBySkuCodeIn(List<String> skuCode);
}
