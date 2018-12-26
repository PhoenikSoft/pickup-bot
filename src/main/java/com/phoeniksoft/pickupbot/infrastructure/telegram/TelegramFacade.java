package com.phoeniksoft.pickupbot.infrastructure.telegram;

import com.phoeniksoft.pickupbot.domain.context.interceptors.exception.CannotRecognizeUserAnswerException;
import com.phoeniksoft.pickupbot.domain.core.*;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class TelegramFacade {

    private PickupBotApi pickupBotApi;

    public Optional<SendMessage> handleMessage(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            SendMessage message = constructUserAnswer(messageText, chatId);
            return Optional.of(message);
        }

        return Optional.empty();
    }

    private SendMessage constructUserAnswer(String messageText, long chatId) {
        SendMessage message = new SendMessage()
                .setChatId(chatId);
        addKeyboardToResponse(message);
        UserQuery query = buildUserQuery(messageText, chatId);
        try {
            UserAdvice advice = pickupBotApi.getAdvice(query);
            message.setText(advice.getMsg() + "\nHelped?");
        } catch (CannotRecognizeUserAnswerException ex) {
            message.setText("Cannot analyze your answer. Please choose some option from the list below.");
        }
        return message;
    }

    private UserQuery buildUserQuery(String messageText, long chatId) {
        UserQuery query;
        if ("New conversation".equals(messageText)) {
            query = UserQuery.builder()
                    .command(UserCommand.GET_START_ADVICE)
                    .build();
        } else {
            query = UserQuery.builder()
                    .command(UserCommand.GET_NEXT_ADVICE)
                    .message(new UserMessage(messageText))
                    .build();
        }

        query.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, chatId);
        return query;
    }

    private void addKeyboardToResponse(SendMessage message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Yes"));
        keyboardFirstRow.add(new KeyboardButton("No"));
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("New conversation"));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}
