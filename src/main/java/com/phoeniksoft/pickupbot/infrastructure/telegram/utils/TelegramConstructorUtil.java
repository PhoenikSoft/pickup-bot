package com.phoeniksoft.pickupbot.infrastructure.telegram.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.TelegramConstants.GET_ADVICE_COMMAND;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramConstructorUtil {

    public static void addKeyboardWithGetAdviceButton(SendMessage message){
        String[][] buttons = {{GET_ADVICE_COMMAND}};
        addKeyboardWithButtons(message, buttons);
    }

    public static void addKeyboardWithButtons(SendMessage message, String[][] buttonsLabels){
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
