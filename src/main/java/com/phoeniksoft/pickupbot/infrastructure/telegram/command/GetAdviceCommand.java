package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.domain.advisor.exception.NoNewAdviceForUserException;
import com.phoeniksoft.pickupbot.domain.core.PickupBotApi;
import com.phoeniksoft.pickupbot.domain.core.UserAdvice;
import com.phoeniksoft.pickupbot.domain.core.UserCommand;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;
import com.phoeniksoft.pickupbot.domain.core.UserQueryParams;
import com.phoeniksoft.pickupbot.domain.notification.Topic;
import com.phoeniksoft.pickupbot.infrastructure.telegram.utils.InlineButtonData;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.addInlineKeyboardWithButtons;
import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.addKeyboardWithButtons;
import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.constructCallbackAnswer;

@AllArgsConstructor
@Slf4j
public class GetAdviceCommand extends SendMessageListCommand {

    private final PickupBotApi pickupBotApi;
    private final CommandConfig commandConfig;

    @Override
    protected List<SendMessage> getMessages(TelegramCommandInput input) {
        UserQuery query = UserQuery.builder()
                .command(commandConfig.command)
                .build();
        query.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, input.getChatId());
        UserAdvice advice;
        try {
            advice = pickupBotApi.getAdvice(query);
        } catch (NoNewAdviceForUserException ex) {
            log.info(ex.getMessage());
            SendMessage noMessagesMessage = newSendMessage(input).setText(ALL_MESSAGES_SHOWN_MSG);
            SendMessage oldAdviceMessage = newSendMessage(input).setText(ex.getOldAdvice().getMsg());
            String[][] buttons = {{commandConfig.commandLabel}, {RETURN_TO_MAIN_MENU_COMMAND}};
            addKeyboardWithButtons(oldAdviceMessage, buttons);
            return Arrays.asList(noMessagesMessage, oldAdviceMessage);
        }

        SendMessage adviceMessage = newSendMessage(input).setText(advice.getMsg());
        String likeButtonData = constructCallbackAnswer(GOOD_ADVICE_COMMAND, advice.getPayload().get(UserAdvice.ADVICE_ID_PARAM).toString());
        String dislikeButtonData = constructCallbackAnswer(BAD_ADVICE_COMMAND, advice.getPayload().get(UserAdvice.ADVICE_ID_PARAM).toString());
        InlineButtonData[][] adviceMessageButtons = {{
                new InlineButtonData(GOOD_ADVICE_COMMAND, likeButtonData),
                new InlineButtonData(BAD_ADVICE_COMMAND, dislikeButtonData)
        }};
        addInlineKeyboardWithButtons(adviceMessage, adviceMessageButtons);

        SendMessage subscribeMessage = newSendMessage(input).setText(getSubscribeMessage());
        String subscribeButtonData = constructCallbackAnswer(SUBSCRIBE_COMMAND, commandConfig.topic.name());
        InlineButtonData[][] subscribeMessageButtons = {{
                new InlineButtonData(SUBSCRIBE_COMMAND, subscribeButtonData)
        }};
        addInlineKeyboardWithButtons(subscribeMessage, subscribeMessageButtons);

        SendMessage nextActionsMessage = newSendMessage(input).setText(CHOOSE_OPTION_MSG);
        String[][] nextMessageButtons = {{commandConfig.commandLabel}, {RETURN_TO_MAIN_MENU_COMMAND}};
        addKeyboardWithButtons(nextActionsMessage, nextMessageButtons);

        return Arrays.asList(adviceMessage, subscribeMessage, nextActionsMessage);
    }

    private String getSubscribeMessage() {
        switch (commandConfig.topic) {
            case MESSAGE:
                return String.format(SUBSCRIBE_MSG, MESSAGES_LABEL);
            case DATE:
                return String.format(SUBSCRIBE_MSG, DATE_LABEL);
            case PROFILE:
                return String.format(SUBSCRIBE_MSG, PROFILE_LABEL);
            default:
                throw new IllegalArgumentException("Unknown topic!");
        }
    }

    @Value
    public static class CommandConfig {
        private UserCommand command;
        private String commandLabel;
        private Topic topic;
    }
}
