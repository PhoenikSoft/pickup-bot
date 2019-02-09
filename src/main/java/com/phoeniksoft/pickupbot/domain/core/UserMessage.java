package com.phoeniksoft.pickupbot.domain.core;

import lombok.NonNull;
import lombok.Value;

@Value
public final class UserMessage {

    @NonNull
    private final String value;
}
