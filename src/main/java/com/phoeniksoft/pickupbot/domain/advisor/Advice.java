package com.phoeniksoft.pickupbot.domain.advisor;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

/**
 * Represents core module advice object that used in module interactions.
 */
@Data
@Builder
public final class Advice {

    public static final String NEXT_ADVICE_PARAM = "nextAdvice";

    private final String id;
    private final String msg;
    private final AdvicePayload payload = new AdvicePayload();

    final class AdvicePayload extends HashMap<String, Object> {
    }
}
