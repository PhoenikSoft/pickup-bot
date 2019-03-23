package com.phoeniksoft.pickupbot.infrastructure.telegram.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.TelegramConstants.GET_DATE_ADVICE_COMMAND;
import static com.phoeniksoft.pickupbot.infrastructure.telegram.TelegramConstants.GET_MESSAGE_ADVICE_COMMAND;
import static com.phoeniksoft.pickupbot.infrastructure.telegram.TelegramConstants.GET_PROFILE_ADVICE_COMMAND;
import static com.phoeniksoft.pickupbot.infrastructure.telegram.TelegramConstants.RETURN_TO_MAIN_MENU_COMMAND;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramConstructorUtil {

    private static final String CALLBACK_ANSWER_DELIMITER = "_";

    public static void addKeyboardWithMainMenuButtons(SendMessage message) {
        String[][] buttons = {{GET_MESSAGE_ADVICE_COMMAND}, {GET_DATE_ADVICE_COMMAND}, {GET_PROFILE_ADVICE_COMMAND}};
        addKeyboardWithButtons(message, buttons);
    }

    public static void addKeyboardWithReturnToMainMenuButton(SendMessage message) {
        String[][] buttons = {{RETURN_TO_MAIN_MENU_COMMAND}};
        addKeyboardWithButtons(message, buttons);
    }

    public static void addKeyboardWithButtons(SendMessage message, String[][] buttonsLabels) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        for (String[] row : buttonsLabels) {
            KeyboardRow keyboardRow = new KeyboardRow();
            for (String label : row) {
                keyboardRow.add(new KeyboardButton(label));
            }
            keyboard.add(keyboardRow);
        }
        addKeyboardWithListButtons(message, keyboard);
    }

    public static void addInlineKeyboardWithButtons(SendMessage message, InlineButtonData[][] buttons) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (InlineButtonData[] buttonRow : buttons) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            for (InlineButtonData buttonData : buttonRow) {
                rowInline.add(new InlineKeyboardButton()
                        .setText(buttonData.getLabel())
                        .setCallbackData(buttonData.getData()));
            }
            rowsInline.add(rowInline);
        }
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
    }

    /**
     * This data will be sent to the callback inline button. The first parameter is mandatory
     */
    public static String constructCallbackAnswer(String commandName, String... dataPieces) {
        return Stream.concat(Stream.of(commandName), Stream.of(dataPieces))
                .collect(Collectors.joining(CALLBACK_ANSWER_DELIMITER));
    }

    public static String[] parseCallbackAnswer(String answer) {
        return answer.split(CALLBACK_ANSWER_DELIMITER);
    }

    public static AnswerCallbackQuery thanksAlert(CallbackQuery callbackQuery, String thanksMsg) {
        return new AnswerCallbackQuery()
                .setCallbackQueryId(callbackQuery.getId())
                .setText(thanksMsg);
    }

    public static EditMessageReplyMarkup removeInlineButtonsCommand(CallbackQuery callbackQuery) {
        return new EditMessageReplyMarkup()
                .setChatId(callbackQuery.getMessage().getChatId())
                .setMessageId(callbackQuery.getMessage().getMessageId());
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
