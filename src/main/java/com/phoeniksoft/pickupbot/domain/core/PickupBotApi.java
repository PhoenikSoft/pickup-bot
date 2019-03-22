package com.phoeniksoft.pickupbot.domain.core;

/**
 * Entry point for interacting with Pickup Bot API.
 */
public interface PickupBotApi {

    UserAdvice getAdvice(UserQuery userQuery);

    void saveUserAnswer(UserQuery userQuery);
}
