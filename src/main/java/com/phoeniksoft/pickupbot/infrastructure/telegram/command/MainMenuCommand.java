package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.addKeyboardWithMainMenuButtons;

public class MainMenuCommand extends SendMessageCommand {

    @Override
    protected void fillMessage(SendMessage message, TelegramCommandInput input) {
        message.setText(CHOOSE_OPTION_MSG);
        addKeyboardWithMainMenuButtons(message);
    }
}
