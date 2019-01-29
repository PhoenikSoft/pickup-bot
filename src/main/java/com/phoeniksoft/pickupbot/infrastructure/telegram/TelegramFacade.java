package com.phoeniksoft.pickupbot.infrastructure.telegram;

import com.phoeniksoft.pickupbot.infrastructure.telegram.command.IllegalUserTextCommand;
import com.phoeniksoft.pickupbot.infrastructure.telegram.command.SendMessageCommand;
import com.phoeniksoft.pickupbot.infrastructure.telegram.command.TelegramCommandInput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class TelegramFacade {

    private final Map<String, SendMessageCommand> commands;
    private final IllegalUserTextCommand defaultCommand;

    public Optional<SendMessage> handleMessage(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            SendMessage message = commands.getOrDefault(messageText, defaultCommand)
                    .execute(TelegramCommandInput.of(update));
            return Optional.of(message);
        }

        return Optional.empty();
    }
}
