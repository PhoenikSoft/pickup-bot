package com.phoeniksoft.pickupbot.infrastructure.telegram.notification;

import com.phoeniksoft.pickupbot.domain.core.user.UserStore;
import com.phoeniksoft.pickupbot.domain.notification.GlobalMessage;
import com.phoeniksoft.pickupbot.domain.notification.NotificationService;
import com.phoeniksoft.pickupbot.domain.notification.SubscriptionService;
import com.phoeniksoft.pickupbot.domain.notification.Topic;
import com.phoeniksoft.pickupbot.infrastructure.telegram.PickupBot;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@AllArgsConstructor
@Slf4j
public class TelegramNotificationService implements NotificationService {

    private final UserStore userStore;
    private final PickupBot pickupBot;
    private final SubscriptionService subscriptionService;

    @Override
    public void notifyAll(GlobalMessage globalMessage) {
        userStore.getAll().stream()
                .map(user -> new SendMessage().setChatId(user.getId()).setText(globalMessage.getMsg()))
                .forEach(this::executeWithExceptionHandle);
    }

    @Override
    public void notifyByTopic(GlobalMessage globalMessage, Topic topic) {
        subscriptionService.getUsersSubscribedToTopic(topic).stream()
                .map(user -> new SendMessage().setChatId(user.getId()).setText(globalMessage.getMsg()))
                .forEach(this::executeWithExceptionHandle);
    }

    private void executeWithExceptionHandle(BotApiMethod botApiMethod) {
        try {
            pickupBot.execute(botApiMethod);
        } catch (TelegramApiException e) {
            log.error("Telegram api error", e);
        }
    }
}
