package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.addKeyboardWithGetAdviceButton;

public class StartCommand extends SendMessageCommand {

    @Override
    protected void fillMessage(SendMessage message, TelegramCommandInput input) {
        message.setText(GREETING_MSG);
        addKeyboardWithGetAdviceButton(message);
    }
}
