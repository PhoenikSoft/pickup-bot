package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.domain.core.PickupBotApi;
import com.phoeniksoft.pickupbot.domain.core.UserCommand;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;
import com.phoeniksoft.pickupbot.domain.core.UserQueryParams;
import com.phoeniksoft.pickupbot.domain.notification.Topic;
import com.phoeniksoft.pickupbot.infrastructure.jpa.notification.UserWithTopicDuplicateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Arrays;
import java.util.List;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.parseCallbackAnswer;
import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.removeInlineButtonsCommand;
import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.thanksAlert;

@AllArgsConstructor
@Slf4j
public class TopicSubscribeCommand implements QueryCallbackCommand<List<BotApiMethod>> {

    private final PickupBotApi pickupBotApi;

    @Override
    public List<BotApiMethod> handleCallback(CallbackQuery callbackQuery) {
        String[] parsedAnswer = parseCallbackAnswer(callbackQuery.getData());
        Topic topic = Topic.valueOf(parsedAnswer[1]);

        UserQuery query = UserQuery.builder()
                .command(UserCommand.SUBSCRIBE_TO_TOPIC)
                .build();
        query.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, callbackQuery.getFrom().getId());
        query.getSpecificParams().put(UserQueryParams.TOPIC_PARAM, topic);

        try {
            pickupBotApi.subscribe(query);
        } catch (UserWithTopicDuplicateException ex) {
            log.warn("User has already subscribed", ex);
        }

        AnswerCallbackQuery thanksAlert = thanksAlert(callbackQuery, THANKS_FOR_SUBSCRIBE_MSG);
        EditMessageReplyMarkup removeInlineKeyboardCommand = removeInlineButtonsCommand(callbackQuery);
        return Arrays.asList(thanksAlert, removeInlineKeyboardCommand);
    }
}
