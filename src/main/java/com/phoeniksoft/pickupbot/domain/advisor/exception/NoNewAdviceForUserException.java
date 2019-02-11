package com.phoeniksoft.pickupbot.domain.advisor.exception;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import lombok.Getter;

@Getter
public class NoNewAdviceForUserException extends RuntimeException {

    private Advice oldAdvice;

    public NoNewAdviceForUserException(String userId, Advice oldAdvice) {
        super("All advices have been used for user: " + userId);
        this.oldAdvice = oldAdvice;
    }
}
