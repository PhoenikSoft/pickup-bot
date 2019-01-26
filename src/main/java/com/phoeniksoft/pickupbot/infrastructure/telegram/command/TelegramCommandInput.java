package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import lombok.Value;
import org.telegram.telegrambots.meta.api.objects.Update;

@Value
public class TelegramCommandInput {

    private long chatId;
    private String messageText;

    public static TelegramCommandInput of(Update update) {
        return new TelegramCommandInput(update.getMessage().getChatId(), update.getMessage().getText());
    }
}
