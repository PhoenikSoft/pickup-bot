package com.phoeniksoft.pickupbot.domain.history;

import lombok.Getter;

@Getter
public class TooManyUserProposalsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Too many user proposals. The limit is %s";
    private final int limit;

    public TooManyUserProposalsException(int limit) {
        super(String.format(DEFAULT_MESSAGE, limit));
        this.limit = limit;
    }
}
