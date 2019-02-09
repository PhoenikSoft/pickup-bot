package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.domain.core.*;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Arrays;
import java.util.List;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.parseCallbackAnswer;

@AllArgsConstructor
public class RateAdviceCommand implements QueryCallbackCommand<List<BotApiMethod>> {

    private final PickupBotApi pickupBotApi;

    @Override
    public List<BotApiMethod> handleCallback(CallbackQuery callbackQuery) {
        String[] parsedAnswer = parseCallbackAnswer(callbackQuery.getData());

        UserQuery query = UserQuery.builder()
                .command(UserCommand.RATE_ADVICE)
                .message(new UserMessage(parsedAnswer[1]))
                .build();
        query.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, callbackQuery.getFrom().getId());
        query.getSpecificParams().put(UserQueryParams.ADVICE_ID_PARAM, parsedAnswer[0]);

        pickupBotApi.saveUserAnswer(query);

        AnswerCallbackQuery thanksAlert = new AnswerCallbackQuery()
                .setCallbackQueryId(callbackQuery.getId())
                .setText(THANKS_FOR_FEEDBACK_MSG);
        EditMessageReplyMarkup removeInlineKeyboardCommand = new EditMessageReplyMarkup()
                .setChatId(callbackQuery.getMessage().getChatId())
                .setMessageId(callbackQuery.getMessage().getMessageId());
        return Arrays.asList(thanksAlert, removeInlineKeyboardCommand);
    }
}
