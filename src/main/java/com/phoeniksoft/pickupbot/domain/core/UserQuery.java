package com.phoeniksoft.pickupbot.domain.core;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class UserQuery {

    UserCommand command;
    UserMessage message;
    @Builder.Default
    UserQueryParams specificParams = new UserQueryParams();
}
