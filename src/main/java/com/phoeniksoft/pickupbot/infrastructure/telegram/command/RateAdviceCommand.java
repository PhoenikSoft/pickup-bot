package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.domain.context.UserAnswer;
import com.phoeniksoft.pickupbot.domain.core.*;
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

@AllArgsConstructor
public class RateAdviceCommand implements QueryCallbackCommand<List<BotApiMethod>> {

    private final PickupBotApi pickupBotApi;

    @Override
    public List<BotApiMethod> handleCallback(CallbackQuery callbackQuery) {
        String[] parsedAnswer = parseCallbackAnswer(callbackQuery.getData());
        USER_RATE_ANSWER userAnswer = USER_RATE_ANSWER.getInstance(parsedAnswer[1]);

        UserQuery query = UserQuery.builder()
                .command(UserCommand.RATE_ADVICE)
                .message(new UserMessage(userAnswer.coreAnswer.name()))
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
