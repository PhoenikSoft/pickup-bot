package com.phoeniksoft.pickupbot.infrastructure.telegram;

import com.phoeniksoft.pickupbot.domain.core.PickupBotApi;
import com.phoeniksoft.pickupbot.infrastructure.telegram.command.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("!dev")
public class TelegramConfig implements TelegramConstants {

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
        Map<String, SendMessageCommand> commands = new HashMap<>(4);
        commands.put(START_COMMAND, startCommand());
        commands.put(RETURN_TO_MAIN_MENU_COMMAND, mainMenuCommand());
        commands.put(GET_MESSAGE_ADVICE_COMMAND, getStartAdviceCommand(pickupBotApi));
        commands.put(GET_DATE_ADVICE_COMMAND, getDateAdviceCommand(pickupBotApi));
        commands.put(GET_PROFILE_ADVICE_COMMAND, getProfileAdviceCommand(pickupBotApi));
        commands.put(GOOD_ADVICE_COMMAND, rateAdviceCommand(pickupBotApi));
        commands.put(BAD_ADVICE_COMMAND, rateAdviceCommand(pickupBotApi));
        return new TelegramFacade(Collections.unmodifiableMap(commands), illegalUserTextCommand());
    }

    @Bean
    public StartCommand startCommand() {
        return new StartCommand();
    }

    @Bean
    public MainMenuCommand mainMenuCommand() {
        return new MainMenuCommand();
    }

    @Bean
    public GetMessageAdviceCommand getStartAdviceCommand(PickupBotApi pickupBotApi) {
        return new GetMessageAdviceCommand(pickupBotApi);
    }

    @Bean
    public GetDateAdviceCommand getDateAdviceCommand(PickupBotApi pickupBotApi) {
        return new GetDateAdviceCommand(pickupBotApi);
    }

    @Bean
    public GetProfileAdviceCommand getProfileAdviceCommand(PickupBotApi pickupBotApi) {
        return new GetProfileAdviceCommand(pickupBotApi);
    }

    @Bean
    public RateAdviceCommand rateAdviceCommand(PickupBotApi pickupBotApi) {
        return new RateAdviceCommand(pickupBotApi);
    }

    @Bean
    public IllegalUserTextCommand illegalUserTextCommand() {
        return new IllegalUserTextCommand();
    }
}
