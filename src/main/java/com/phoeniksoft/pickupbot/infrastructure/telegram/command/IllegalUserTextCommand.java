package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class IllegalUserTextCommand extends SendMessageCommand {

    @Override
    protected void fillMessage(SendMessage message, TelegramCommandInput input) {
        message.setText(UNRECOGNIZABLE_USER_ANSWER_ERROR);
    }
}
