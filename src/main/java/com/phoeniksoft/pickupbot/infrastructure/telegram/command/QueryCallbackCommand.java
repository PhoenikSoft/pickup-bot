package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.infrastructure.telegram.TelegramConstants;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface QueryCallbackCommand<T extends BotApiMethod> extends TelegramConstants {

    T handleCallback(CallbackQuery callbackQuery);
}
