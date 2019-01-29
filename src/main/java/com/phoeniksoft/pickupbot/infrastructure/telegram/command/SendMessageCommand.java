package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.addKeyboardWithGetAdviceButton;

@Slf4j
public abstract class SendMessageCommand implements TelegramCommand<SendMessage> {

    private static final String ERROR_MSG = "Unexpected error occurred during command execution for user [%s], input command [%s]";

    @Override
    public SendMessage execute(TelegramCommandInput input) {
        SendMessage message = newSendMessage(input);
        try {
            fillMessage(message, input);
        }catch (Exception ex){
            log.error(String.format(ERROR_MSG, input.getChatId(), input.getMessageText()), ex);
            message = newSendMessage(input);
            message.setText(UNEXPECTED_ERROR);
            addKeyboardWithGetAdviceButton(message);
        }
        return message;
    }

    protected abstract void fillMessage(SendMessage message, TelegramCommandInput input);

    private SendMessage newSendMessage(TelegramCommandInput input){
        return new SendMessage().setChatId(input.getChatId());
    }
}
