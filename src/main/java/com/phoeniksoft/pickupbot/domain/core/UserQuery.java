package com.phoeniksoft.pickupbot.domain.core;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public final class UserQuery {

    private final UserCommand command;
    private final Map<String, Object> specificParams;
}
