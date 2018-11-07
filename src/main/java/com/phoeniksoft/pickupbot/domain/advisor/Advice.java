package com.phoeniksoft.pickupbot.domain.advisor;

import lombok.Data;

import java.util.Optional;

@Data
public final class Advice {

    private final String id;
    private final String msg;
    private final Optional<Advice> nextAdvice;
}
