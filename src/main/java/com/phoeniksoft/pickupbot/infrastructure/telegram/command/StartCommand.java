package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.addKeyboardWithMainMenuButtons;

public class StartCommand extends SendMessageCommand {

    @Override
    @SneakyThrows
    protected void fillMessage(SendMessage message, TelegramCommandInput input) {
        message.setText(GREETING_MSG);
        addKeyboardWithMainMenuButtons(message);
    }
}
