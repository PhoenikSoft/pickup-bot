package com.phoeniksoft.pickupbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class PickupBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(PickupBotApplication.class, args);
    }
}
