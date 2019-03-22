package com.phoeniksoft.pickupbot.domain.core;

import lombok.Builder;
import lombok.Value;

/**
 * Input query for Pickup Bot API.
 */
@Value
@Builder
public final class UserQuery {

    /**
     * User intent
     */
    UserCommand command;

    /**
     * Message given by user
     */
    UserMessage message;

    /**
     * Params that could help advisor for retrieving appropriate advice.
     */
    @Builder.Default
    UserQueryParams specificParams = new UserQueryParams();
}
