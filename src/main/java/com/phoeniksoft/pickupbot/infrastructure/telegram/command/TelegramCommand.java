package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

import com.phoeniksoft.pickupbot.infrastructure.telegram.TelegramConstants;

public interface TelegramCommand <T> extends TelegramConstants {

    T execute(TelegramCommandInput input);
}
