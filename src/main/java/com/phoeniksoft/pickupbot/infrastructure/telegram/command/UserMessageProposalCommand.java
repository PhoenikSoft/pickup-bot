package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.infrastructure.telegram.utils.UsersTransactionsStorage;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@AllArgsConstructor
public class UserMessageProposalCommand extends SendMessageCommand {

    private final UsersTransactionsStorage usersTransactionsStorage;

    @Override
    protected void fillMessage(SendMessage message, TelegramCommandInput input) {
        message.setText(SEND_YOUR_MESSAGE_MSG);
        usersTransactionsStorage.put(input.getChatId(), true);
    }
}
