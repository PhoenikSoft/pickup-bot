package com.phoeniksoft.pickupbot.infrastructure.telegram.command;

public interface Applicable {

    boolean isApplicable(TelegramCommandInput input);
}
