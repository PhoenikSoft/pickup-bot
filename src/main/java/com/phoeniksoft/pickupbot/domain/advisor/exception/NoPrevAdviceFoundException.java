package com.phoeniksoft.pickupbot.domain.advisor.exception;

public class NoPrevAdviceFoundException extends RuntimeException {

    private static final String CANNOT_FIND_PREVIOUS_ADVICE = "Cannot find previous advice";

    public NoPrevAdviceFoundException() {
        super(CANNOT_FIND_PREVIOUS_ADVICE);
    }
}
