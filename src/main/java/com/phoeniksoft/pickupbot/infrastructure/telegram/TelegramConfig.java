package com.phoeniksoft.pickupbot.infrastructure.telegram;

import com.phoeniksoft.pickupbot.domain.core.PickupBotApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!dev")
public class TelegramConfig {

    @Bean
    public TelegramInitializer telegramInitializer() {
        return new TelegramInitializer();
    }

    @Bean
    @DependsOn("telegramInitializer")
    public PickupBot pickupBot(TelegramFacade telegramFacade) {
        return new PickupBot(telegramFacade);
    }

    @Bean
    public TelegramFacade telegramFacade(PickupBotApi pickupBotApi) {
        return new TelegramFacade(pickupBotApi);
    }
}
