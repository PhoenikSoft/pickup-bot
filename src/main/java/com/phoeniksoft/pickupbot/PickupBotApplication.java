package com.phoeniksoft.pickupbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PickupBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickupBotApplication.class, args);
    }
}
