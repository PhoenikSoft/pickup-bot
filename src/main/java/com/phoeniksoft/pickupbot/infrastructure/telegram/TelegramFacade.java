package com.phoeniksoft.pickupbot.infrastructure.telegram;

import com.phoeniksoft.pickupbot.infrastructure.telegram.command.IllegalUserTextCommand;
import com.phoeniksoft.pickupbot.infrastructure.telegram.command.QueryCallbackCommand;
import com.phoeniksoft.pickupbot.infrastructure.telegram.command.SaveUserProposalCommand;
import com.phoeniksoft.pickupbot.infrastructure.telegram.command.SendMessageCommand;
import com.phoeniksoft.pickupbot.infrastructure.telegram.command.SendMessageListCommand;
import com.phoeniksoft.pickupbot.infrastructure.telegram.command.TelegramCommandInput;
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

/**
 * The facade component that identifies the type of the user input and delegate handling to the appropriate command.
 */
@AllArgsConstructor
@Slf4j
public class TelegramFacade {

    /**
     * Command that saves user free written text.
     */
    private final SaveUserProposalCommand saveUserProposalCommand;
    /**
     * Commands that return only one response message.
     */
    private final Map<String, SendMessageCommand> oneMessageCommands;
    /**
     * Commands that return few response messages.
     */
    private final Map<String, SendMessageListCommand> fewMessagesCommands;
    /**
     * Command that handle user responses from callback pinned buttons.
     */
    private final Map<String, QueryCallbackCommand> callbackCommands;
    /**
     * Command that is executed when user answer cannot be identified.
     */
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
            if (saveUserProposalCommand.isApplicable(commandInput)) {
                SendMessage message = saveUserProposalCommand.execute(commandInput);
                return Arrays.asList(Optional.of(message));
            }

            return Arrays.asList(Optional.of(defaultCommand.execute(commandInput)));
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String command = parseCallbackAnswer(callbackQuery.getData())[0];
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
