package com.example.orderservice.service;

import com.example.orderservice.client.StockClient;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.StockResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderLineItems;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    @Value("${spring.kafka.template.default-topic}")
    private String orderTopic;

    private final OrderRepository orderRepository;
    private final StockClient stockClient;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final OrderMapper orderMapper;

    public String placeOrder(OrderRequest orderRequest) throws JsonProcessingException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderMapper::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodeList = orderLineItems.stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // Call Stock Service to check if all products are in stock
        ResponseEntity<List<StockResponse>> stockResponse = stockClient.isInStock(skuCodeList);
        List<StockResponse> stockResponseList = stockResponse.getBody();

        boolean allProductsInStock = stockResponseList.stream()
                .allMatch(StockResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
            String orderAsMessage = objectMapper.writeValueAsString(order.getOrderNumber());
            kafkaTemplate.send(orderTopic, orderAsMessage);
            log.info("Order placed {}", orderAsMessage);
            return "Order placed successfully!";
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }
}