package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.domain.context.UserAnswer;
import com.phoeniksoft.pickupbot.domain.core.PickupBotApi;
import com.phoeniksoft.pickupbot.domain.core.UserCommand;
import com.phoeniksoft.pickupbot.domain.core.UserMessage;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;
import com.phoeniksoft.pickupbot.domain.core.UserQueryParams;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.parseCallbackAnswer;
import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.removeInlineButtonsCommand;
import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.thanksAlert;

@AllArgsConstructor
public class RateAdviceCommand implements QueryCallbackCommand<List<BotApiMethod>> {

    private final PickupBotApi pickupBotApi;

    @Override
    public List<BotApiMethod> handleCallback(CallbackQuery callbackQuery) {
        String[] parsedAnswer = parseCallbackAnswer(callbackQuery.getData());
        USER_RATE_ANSWER userAnswer = USER_RATE_ANSWER.getInstance(parsedAnswer[0]);

        UserQuery query = UserQuery.builder()
                .command(UserCommand.RATE_ADVICE)
                .message(new UserMessage(userAnswer.coreAnswer.name()))
                .build();
        query.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, callbackQuery.getFrom().getId());
        query.getSpecificParams().put(UserQueryParams.ADVICE_ID_PARAM, parsedAnswer[1]);

        pickupBotApi.saveUserAnswer(query);

        AnswerCallbackQuery thanksAlert = thanksAlert(callbackQuery, THANKS_FOR_FEEDBACK_MSG);
        EditMessageReplyMarkup removeInlineKeyboardCommand = removeInlineButtonsCommand(callbackQuery);
        return Arrays.asList(thanksAlert, removeInlineKeyboardCommand);
    }

    private enum USER_RATE_ANSWER {
        LIKE(GOOD_ADVICE_COMMAND, UserAnswer.LIKE_ADVICE), DISLIKE(BAD_ADVICE_COMMAND, UserAnswer.DISLIKE_ADVICE);

        private final String telegramLabel;
        private final UserAnswer coreAnswer;

        USER_RATE_ANSWER(String telegramLabel, UserAnswer coreAnswer) {
            this.telegramLabel = telegramLabel;
            this.coreAnswer = coreAnswer;
        }

        public static USER_RATE_ANSWER getInstance(String label) {
            return Stream.of(values())
                    .filter(answer -> answer.telegramLabel.equals(label))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException());
        }
    }
}
