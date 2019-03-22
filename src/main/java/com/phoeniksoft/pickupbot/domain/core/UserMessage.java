package com.phoeniksoft.pickupbot.domain.core;

import lombok.NonNull;
import lombok.Value;

/**
 * Message entered by user.
 */
@Value
public final class UserMessage {

    @NonNull
    private final String value;
}
