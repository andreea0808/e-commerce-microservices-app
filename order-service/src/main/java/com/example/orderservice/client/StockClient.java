package com.example.orderservice.client;

import com.example.orderservice.dto.StockResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "stock-service", url = "${application.feign.stock-service.url}")
public interface StockClient {

    @GetMapping
    ResponseEntity<List<StockResponse>> isInStock(@RequestParam List<String> skuCode);
}
