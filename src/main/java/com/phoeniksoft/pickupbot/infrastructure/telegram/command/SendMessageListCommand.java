package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.addKeyboardWithReturnToMainMenuButton;

@Slf4j
public abstract class SendMessageListCommand implements TelegramCommand<List<SendMessage>> {

    private static final String ERROR_MSG = "Unexpected error occurred during command execution for user [%s], input command [%s]";

    @Override
    public List<SendMessage> execute(TelegramCommandInput input) {
        List<SendMessage> messages;
        try {
            messages = getMessages(input);
        } catch (Exception ex) {
            log.error(String.format(ERROR_MSG, input.getChatId(), input.getMessageText()), ex);
            SendMessage errorMessage = newSendMessage(input);
            errorMessage.setText(UNEXPECTED_ERROR);
            addKeyboardWithReturnToMainMenuButton(errorMessage);
            messages = Arrays.asList(errorMessage);
        }

        return messages;
    }

    protected abstract List<SendMessage> getMessages(TelegramCommandInput input);

    protected SendMessage newSendMessage(TelegramCommandInput input) {
        return new SendMessage().setChatId(input.getChatId());
    }
}
