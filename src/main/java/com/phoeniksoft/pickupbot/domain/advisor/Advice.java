package com.phoeniksoft.pickupbot.domain.advisor;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public final class Advice {

    public static final String NEXT_ADVICE_PARAM = "nextAdvice";

    private final String id;
    private final String msg;
    private final Map<String, Object> params = new HashMap<>();
}
