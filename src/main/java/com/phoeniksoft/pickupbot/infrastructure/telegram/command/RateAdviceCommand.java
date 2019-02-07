package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.domain.core.*;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.parseCallbackAnswer;

@AllArgsConstructor
public class RateAdviceCommand implements QueryCallbackCommand<AnswerCallbackQuery> {

    private final PickupBotApi pickupBotApi;

    @Override
    public AnswerCallbackQuery handleCallback(CallbackQuery callbackQuery) {
        String[] parsedAnswer = parseCallbackAnswer(callbackQuery.getData());

        UserQuery query = UserQuery.builder()
                .command(UserCommand.RATE_ADVICE)
                .message(new UserMessage(parsedAnswer[1]))
                .build();
        query.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, callbackQuery.getFrom().getId());
        query.getSpecificParams().put(UserQueryParams.ADVICE_ID_PARAM, parsedAnswer[0]);

        pickupBotApi.saveUserAnswer(query);

        return new AnswerCallbackQuery()
                .setCallbackQueryId(callbackQuery.getId())
                .setText(THANKS_FOR_FEEDBACK_MSG);
    }
}
