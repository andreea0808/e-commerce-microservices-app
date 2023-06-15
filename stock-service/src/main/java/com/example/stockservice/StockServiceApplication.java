package com.example.stockservice;

import com.example.stockservice.entity.Stock;
import com.example.stockservice.repository.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(StockRepository stockRepository) {
		return args -> {
			Stock stock = new Stock();
			stock.setSkuCode("iphone_13");
			stock.setQuantity(100);

			Stock stock1 = new Stock();
			stock1.setSkuCode("iphone_13_red");
			stock1.setQuantity(23);

			stockRepository.save(stock);
			stockRepository.save(stock1);
		};
	}
}
