package com.jocata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r.path("/api/v1/**")
                        .uri("lb://PROMOTION-SERVICE"))
                .route("promotions-service", r -> r.path("/api/products/**")
                        .uri("lb://PRODUCT-SERVICE"))
                .route("rating-service", r -> r.path("/api/v1/**")
                        .uri("lb://RATING-SERVICE"))
                .route("shipping-service", r -> r.path("/api/v1/**")
                        .uri("lb://SHIPPING-SERVICE"))
                .route("transaction-service", r -> r.path("/api/v1/**")
                        .uri("lb://TRANSACTION-SERVICE"))
                .route("order-service", r -> r.path("/api/orders/**")
                        .uri("lb://PRODUCT-SERVICE"))
                .route("user-service", r -> r.path("/api/v1/**")
                        .uri("lb://USER-SERVICE")).build();
    }
}