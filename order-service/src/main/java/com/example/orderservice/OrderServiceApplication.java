package com.example.orderservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Andreea Oprea",
                        email = "andreea.oprea0808@gmail.com",
                        url = "https://www.linkedin.com/in/andreea-oprea-614912168/"
                ),
                title = "Order Service API",
                version = "1.0",
                description = "Documentation Order Service API v1.0",
                license = @License(
                        name = "License name",
                        url = "http://some-license-url.com"
                )
        ),
        servers = {
                @Server(
                        description = "Local server",
                        url = "http://localhost:8081"
                )
        }
)
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
