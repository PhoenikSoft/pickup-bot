package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.domain.core.PickupBotApi;
import com.phoeniksoft.pickupbot.domain.core.UserCommand;
import com.phoeniksoft.pickupbot.domain.core.UserMessage;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;
import com.phoeniksoft.pickupbot.domain.core.UserQueryParams;
import com.phoeniksoft.pickupbot.domain.history.TooManyUserProposalsException;
import com.phoeniksoft.pickupbot.infrastructure.telegram.utils.UsersTransactionsStorage;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.addKeyboardWithMainMenuButtons;

@AllArgsConstructor
public class SaveUserProposalCommand extends SendMessageCommand implements Applicable {

    private final UsersTransactionsStorage usersTransactionsStorage;
    private final PickupBotApi pickupBotApi;

    @Override
    public boolean isApplicable(TelegramCommandInput input) {
        Boolean isUserStartedInputTransaction = usersTransactionsStorage.get(input.getChatId());
        return BooleanUtils.isTrue(isUserStartedInputTransaction);
    }

    @Override
    protected void fillMessage(SendMessage message, TelegramCommandInput input) {
        UserQuery query = UserQuery.builder()
                .command(UserCommand.ADD_USER_PROPOSAL)
                .message(new UserMessage(input.getMessageText()))
                .build();
        query.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, input.getChatId());

        String msg;
        try {
            pickupBotApi.saveUserProposal(query);
            msg = THANK_YOU_FOR_YOUR_MESSAGE_MSG;
        } catch (TooManyUserProposalsException ex) {
            msg = USER_PROPOSALS_LIMIT_EXCEEDED_MSG;
        } finally {
            usersTransactionsStorage.remove(input.getChatId());
        }

        message.setText(msg);
        addKeyboardWithMainMenuButtons(message);
    }
}
