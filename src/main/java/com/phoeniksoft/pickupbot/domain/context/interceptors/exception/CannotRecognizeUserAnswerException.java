package com.phoeniksoft.pickupbot.domain.context.interceptors.exception;

public class CannotRecognizeUserAnswerException extends RuntimeException {

    public CannotRecognizeUserAnswerException(String message) {
        super(message);
    }

    public CannotRecognizeUserAnswerException(String message, Throwable cause) {
        super(message, cause);
    }
}
