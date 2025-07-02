package com.jocata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication
@EntityScan("com.jocata.shippingservice.entity")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}