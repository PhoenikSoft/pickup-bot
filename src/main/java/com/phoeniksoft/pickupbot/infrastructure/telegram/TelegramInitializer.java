package com.phoeniksoft.pickupbot.infrastructure.telegram;

import org.telegram.telegrambots.ApiContextInitializer;

import javax.annotation.PostConstruct;

public class TelegramInitializer {

    @PostConstruct
    private void initTelegramContext() {
        ApiContextInitializer.init();
    }
}
