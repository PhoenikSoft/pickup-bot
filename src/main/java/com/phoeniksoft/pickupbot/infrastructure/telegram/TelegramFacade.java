package com.phoeniksoft.pickupbot.infrastructure.telegram;

import com.phoeniksoft.pickupbot.infrastructure.telegram.command.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.phoeniksoft.pickupbot.infrastructure.telegram.utils.TelegramConstructorUtil.parseCallbackAnswer;

@AllArgsConstructor
@Slf4j
public class TelegramFacade {

    private final Map<String, SendMessageCommand> oneMessageCommands;
    private final Map<String, SendMessageListCommand> fewMessagesCommands;
    private final Map<String, QueryCallbackCommand> callbackCommands;
    private final IllegalUserTextCommand defaultCommand;

    public List<Optional<? extends BotApiMethod>> handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            TelegramCommandInput commandInput = TelegramCommandInput.of(update);
            SendMessageCommand oneMessageCommand = oneMessageCommands.get(messageText);
            if (oneMessageCommand != null) {
                SendMessage message = oneMessageCommand.execute(commandInput);
                return Arrays.asList(Optional.of(message));
            }
            SendMessageListCommand fewMessagesCommand = fewMessagesCommands.get(messageText);
            if (fewMessagesCommand != null) {
                return fewMessagesCommand.execute(commandInput).stream()
                        .map(Optional::of)
                        .collect(Collectors.toList());
            }

            return Arrays.asList(Optional.of(defaultCommand.execute(commandInput)));
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String command = parseCallbackAnswer(callbackQuery.getData())[1];
            QueryCallbackCommand queryCallbackCommand = callbackCommands.get(command);
            if (queryCallbackCommand != null) {
                List<BotApiMethod> callbackCommands = queryCallbackCommand.handleCallback(callbackQuery);
                return callbackCommands.stream()
                        .map(Optional::of)
                        .collect(Collectors.toList());
            }
        }

        return Arrays.asList(Optional.empty());
    }
}
