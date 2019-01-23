package com.phoeniksoft.pickupbot.domain.core.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super("Cannot find user with the id: " + userId);
    }
}
