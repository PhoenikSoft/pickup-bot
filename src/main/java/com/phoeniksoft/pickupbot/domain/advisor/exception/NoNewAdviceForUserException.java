package com.phoeniksoft.pickupbot.domain.advisor.exception;

public class NoNewAdviceForUserException extends RuntimeException {

    public NoNewAdviceForUserException(String userId) {
        super("All advices have been used for user: " + userId);
    }
}
