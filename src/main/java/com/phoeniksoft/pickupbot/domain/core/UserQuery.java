package com.phoeniksoft.pickupbot.domain.core;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
@Builder
public final class UserQuery {

    UserCommand command;
    UserMessage message;
    UserQueryParams specificParams = new UserQueryParams();
}
