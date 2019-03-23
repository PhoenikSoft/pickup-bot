package com.phoeniksoft.pickupbot.infrastructure.jpa.notification;

public class UserWithTopicDuplicateException extends RuntimeException {

    public UserWithTopicDuplicateException(String message) {
        super(message);
    }

    public UserWithTopicDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
