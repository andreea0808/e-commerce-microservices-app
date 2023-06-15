package com.example.emailservice.kafka;

import com.example.emailservice.event.OrderPlacedEvent;
import com.example.emailservice.service.EmailSenderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailConsumer {

    @Value("${spring.mail.customer}")
    private String customerEmail;

    private final ObjectMapper objectMapper;

    private final EmailSenderService mailService;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(String message) throws JsonProcessingException {
        OrderPlacedEvent orderPlacedEvent = objectMapper.readValue(message, OrderPlacedEvent.class);

        mailService.sendEmail(
                customerEmail,
                "Order placed successfully",
                "Order placed successfully. Order number: " + orderPlacedEvent.getOrderNumber());
        log.info("Order event received in email service => {}", message);
    }
}