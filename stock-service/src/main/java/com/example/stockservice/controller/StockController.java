package com.example.stockservice.controller;

import com.example.stockservice.dto.StockResponse;
import com.example.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public ResponseEntity<List<StockResponse>> isInStock(@RequestParam List<String> skuCode) throws InterruptedException {
        return ResponseEntity.ok(stockService.isInStock(skuCode));
    }
}