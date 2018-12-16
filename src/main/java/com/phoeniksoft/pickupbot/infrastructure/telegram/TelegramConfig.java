package com.phoeniksoft.pickupbot.infrastructure.telegram;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class TelegramConfig {

    @Bean
    public TelegramInitializer telegramInitializer() {
        return new TelegramInitializer();
    }

    @Bean
    @DependsOn("telegramInitializer")
    public PickupBot pickupBot() {
        return new PickupBot();
    }
}
