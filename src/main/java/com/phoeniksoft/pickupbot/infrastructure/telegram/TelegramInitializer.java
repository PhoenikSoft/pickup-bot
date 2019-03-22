package com.phoeniksoft.pickupbot.infrastructure.telegram;

import org.telegram.telegrambots.ApiContextInitializer;

import javax.annotation.PostConstruct;

/**
 * Component that initializes Telegram Bot context (session, webhook).
 */
public class TelegramInitializer {

    @PostConstruct
    private void initTelegramContext() {
        ApiContextInitializer.init();
    }
}
