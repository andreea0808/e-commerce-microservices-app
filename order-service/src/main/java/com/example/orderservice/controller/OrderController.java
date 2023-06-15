package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Tag(name = "Order Controller")
public class OrderController {

    private final OrderService orderService;

    @Operation(
            description = "Post endpoint to place an order",
            summary = "Place an order",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order placed successfully"
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Stock Service is down, please try again later!"
                    )
            }
    )
    @PostMapping
    @CircuitBreaker(name = "stock", fallbackMethod = "fallback")
    @TimeLimiter(name = "stock")
    @Retry(name = "stock")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return orderService.placeOrder(orderRequest);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<String> fallback(OrderRequest orderRequest, RuntimeException e) {
        return CompletableFuture.supplyAsync(() -> "Stock Service is down, please try again later!");
    }
}