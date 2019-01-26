package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.domain.advisor.exception.NoNewStartAdviceForUserException;
import com.phoeniksoft.pickupbot.domain.core.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.addKeyboardWithButtons;
import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.addKeyboardWithGetAdviceButton;

@AllArgsConstructor
@Slf4j
public class GetStartAdviceCommand extends SendMessageCommand {

    private final PickupBotApi pickupBotApi;

    @Override
    protected void fillMessage(SendMessage message, TelegramCommandInput input) {
        UserQuery query = UserQuery.builder()
                .command(UserCommand.GET_START_ADVICE)
                .build();
        query.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, message.getChatId());
        try {
            UserAdvice advice = pickupBotApi.getAdvice(query);
            message.setText(advice.getMsg());
            String[][] buttons = {{GOOD_ADVICE_COMMAND, BAD_ADVICE_COMMAND}};
            addKeyboardWithButtons(message, buttons);
        }catch (NoNewStartAdviceForUserException ex){
            log.info(ex.getMessage());
            message.setText(ALL_MESSAGES_SHOWN_MSG);
            addKeyboardWithGetAdviceButton(message);
        }
    }
}
