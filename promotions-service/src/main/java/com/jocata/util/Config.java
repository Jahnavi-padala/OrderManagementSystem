package com.jocata.util;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Config {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
