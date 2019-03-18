package com.phoeniksoft.pickupbot.infrastructure.telegram;

import com.phoeniksoft.pickupbot.domain.core.PickupBotApi;
import com.phoeniksoft.pickupbot.domain.core.user.UserStore;
import com.phoeniksoft.pickupbot.domain.notification.NotificationService;
import com.phoeniksoft.pickupbot.infrastructure.telegram.command.*;
import com.phoeniksoft.pickupbot.infrastructure.telegram.notification.TelegramNotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean configuration of the telegram components.
 */
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
        Map<String, SendMessageCommand> oneMessageCommands = new HashMap<>(2);
        oneMessageCommands.put(START_COMMAND, startCommand());
        oneMessageCommands.put(RETURN_TO_MAIN_MENU_COMMAND, mainMenuCommand());
        Map<String, SendMessageListCommand> manyMessagesCommands = new HashMap<>(3);
        manyMessagesCommands.put(GET_MESSAGE_ADVICE_COMMAND, getStartAdviceCommand(pickupBotApi));
        manyMessagesCommands.put(GET_DATE_ADVICE_COMMAND, getDateAdviceCommand(pickupBotApi));
        manyMessagesCommands.put(GET_PROFILE_ADVICE_COMMAND, getProfileAdviceCommand(pickupBotApi));
        Map<String, QueryCallbackCommand> callbackCommands = new HashMap<>(2);
        callbackCommands.put(GOOD_ADVICE_COMMAND, rateAdviceCommand(pickupBotApi));
        callbackCommands.put(BAD_ADVICE_COMMAND, rateAdviceCommand(pickupBotApi));
        return new TelegramFacade(Collections.unmodifiableMap(oneMessageCommands),
                Collections.unmodifiableMap(manyMessagesCommands),
                Collections.unmodifiableMap(callbackCommands),
                illegalUserTextCommand());
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

    @Bean
    public NotificationService notificationService(UserStore userStore, PickupBot pickupBot) {
        return new TelegramNotificationService(userStore, pickupBot);
    }
}
