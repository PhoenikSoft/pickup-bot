package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.domain.core.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.addKeyboardWithButtons;

@AllArgsConstructor
@Slf4j
public class RateAdviceCommand extends SendMessageCommand {

    private final PickupBotApi pickupBotApi;

    @Override
    protected void fillMessage(SendMessage message, TelegramCommandInput input) {
        UserQuery query = UserQuery.builder()
                .command(UserCommand.RATE_ADVICE)
                .message(new UserMessage(input.getMessageText()))
                .build();
        query.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, message.getChatId());
        pickupBotApi.saveUserAnswer(query);
        if (GOOD_ADVICE_COMMAND.equals(input.getMessageText())) {
            message.setText(GOOD_ADVICE_ANSWER_MSG);
        } else {
            message.setText(BAD_ADVICE_ANSWER_MSG);
        }
        String[][] buttons = {{GET_MESSAGE_ADVICE_COMMAND}, {RETURN_TO_MAIN_MENU_COMMAND}};
        addKeyboardWithButtons(message, buttons);
    }
}
