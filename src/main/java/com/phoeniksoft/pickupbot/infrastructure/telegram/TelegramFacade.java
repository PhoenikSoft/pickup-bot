package com.phoeniksoft.pickupbot.infrastructure.telegram;

import com.phoeniksoft.pickupbot.domain.advisor.exception.NoNewStartAdviceForUserException;
import com.phoeniksoft.pickupbot.domain.core.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class TelegramFacade implements TelegramConstants {

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
        SendMessage message = new SendMessage().setChatId(chatId);

        if (START_COMMAND.equals(messageText)) {
            message.setText(GREETING_MSG);
            addKeyboardWithGetAdviceButton(message);
        } else if (GET_ADVICE_COMMAND.equals(messageText)) {
            UserQuery query = UserQuery.builder()
                    .command(UserCommand.GET_START_ADVICE)
                    .build();
            query.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, chatId);
            try {
                UserAdvice advice = pickupBotApi.getAdvice(query);
                message.setText(advice.getMsg());
                String[][] buttons = {{GOOD_ADVICE_COMMAND, BAD_ADVICE_COMMAND}};
                addKeyboardWithButtons(message, buttons);
            }catch (NoNewStartAdviceForUserException ex){
                log.info(ex.getMessage());
                message.setText(ALL_MESSAGES_SHOWN_MSG);
                addKeyboardWithGetAdviceButton(message);
            }
        } else if (Arrays.asList(GOOD_ADVICE_COMMAND, BAD_ADVICE_COMMAND).contains(messageText)) {
            UserQuery query = UserQuery.builder()
                    .command(UserCommand.RATE_ADVICE)
                    .message(new UserMessage(messageText))
                    .build();
            query.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, chatId);
            pickupBotApi.saveUserAnswer(query);
            if(GOOD_ADVICE_COMMAND.equals(messageText)){
                message.setText(GOOD_ADVICE_ANSWER_MSG);
            }else{
                message.setText(BAD_ADVICE_ANSWER_MSG);
            }
            addKeyboardWithGetAdviceButton(message);
        } else {
            message.setText(UNRECOGNIZABLE_USER_ANSWER_ERROR);
            addKeyboardWithGetAdviceButton(message);
        }
        return message;
    }

    private static void addKeyboardWithGetAdviceButton(SendMessage message){
        String[][] buttons = {{GET_ADVICE_COMMAND}};
        addKeyboardWithButtons(message, buttons);
    }

    private static void addKeyboardWithButtons(SendMessage message, String[][] buttonsLabels){
        List<KeyboardRow> keyboard = new ArrayList<>();
        for(String[] row : buttonsLabels){
            KeyboardRow keyboardRow = new KeyboardRow();
            for(String label : row){
                keyboardRow.add(new KeyboardButton(label));
            }
            keyboard.add(keyboardRow);
        }
        addKeyboardWithListButtons(message, keyboard);
    }

    private static void addKeyboardWithListButtons(SendMessage message, List<KeyboardRow> keyboard) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}
