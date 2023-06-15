package com.example.stockservice.service;

import com.example.stockservice.dto.StockResponse;
import com.example.stockservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final StockRepository stockRepository;

    @Transactional(readOnly = true)
    public List<StockResponse> isInStock(List<String> skuCode) throws InterruptedException {
        return stockRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(stock ->
                        StockResponse.builder()
                                .skuCode(stock.getSkuCode())
                                .isInStock(stock.getQuantity() > 0)
                                .build()
                ).toList();
    }
}
