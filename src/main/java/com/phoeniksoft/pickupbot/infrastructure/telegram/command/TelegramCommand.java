package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.infrastructure.telegram.TelegramConstants;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

public interface TelegramCommand <T extends PartialBotApiMethod> extends TelegramConstants {

    T execute(TelegramCommandInput input);
}
