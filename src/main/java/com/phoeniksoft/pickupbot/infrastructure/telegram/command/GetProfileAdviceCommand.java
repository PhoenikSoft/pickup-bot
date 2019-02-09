package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.domain.advisor.exception.NoNewAdviceForUserException;
import com.phoeniksoft.pickupbot.domain.core.*;
import com.phoeniksoft.pickupbot.infrastructure.telegram.utils.InlineButtonData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.*;

@AllArgsConstructor
@Slf4j
public class GetProfileAdviceCommand extends SendMessageListCommand {

    private final PickupBotApi pickupBotApi;

    @Override
    protected List<SendMessage> getMessages(TelegramCommandInput input) {
        UserQuery query = UserQuery.builder()
                .command(UserCommand.GET_PROFILE_ADVICE)
                .build();
        query.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, input.getChatId());
        UserAdvice advice;
        try {
            advice = pickupBotApi.getAdvice(query);
        } catch (NoNewAdviceForUserException ex) {
            log.info(ex.getMessage());
            SendMessage noMessagesMessage = newSendMessage(input).setText(ALL_MESSAGES_SHOWN_MSG);
            String[][] buttons = {{GET_PROFILE_ADVICE_COMMAND}, {RETURN_TO_MAIN_MENU_COMMAND}};
            addKeyboardWithButtons(noMessagesMessage, buttons);
            return Arrays.asList(noMessagesMessage);
        }

        SendMessage adviceMessage = newSendMessage(input).setText(advice.getMsg());
        String likeButtonData = constructCallbackAnswer(advice.getPayload().get(UserAdvice.ADVICE_ID_PARAM).toString(), GOOD_ADVICE_COMMAND);
        String dislikeButtonData = constructCallbackAnswer(advice.getPayload().get(UserAdvice.ADVICE_ID_PARAM).toString(), BAD_ADVICE_COMMAND);
        InlineButtonData[][] adviceMessageButtons = {{
                new InlineButtonData(GOOD_ADVICE_COMMAND, likeButtonData),
                new InlineButtonData(BAD_ADVICE_COMMAND, dislikeButtonData)
        }};
        addInlineKeyboardWithButtons(adviceMessage, adviceMessageButtons);

        SendMessage nextActionsMessage = newSendMessage(input).setText(CHOOSE_OPTION_MSG);
        String[][] nextMessageButtons = {{GET_PROFILE_ADVICE_COMMAND}, {RETURN_TO_MAIN_MENU_COMMAND}};
        addKeyboardWithButtons(nextActionsMessage, nextMessageButtons);

        return Arrays.asList(adviceMessage, nextActionsMessage);
    }
}
