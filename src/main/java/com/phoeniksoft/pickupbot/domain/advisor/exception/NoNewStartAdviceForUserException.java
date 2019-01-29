package com.phoeniksoft.pickupbot.domain.advisor.exception;

public class NoNewStartAdviceForUserException extends RuntimeException {

    public NoNewStartAdviceForUserException(String userId) {
        super("All start advices have been used for user: " + userId);
    }
}
