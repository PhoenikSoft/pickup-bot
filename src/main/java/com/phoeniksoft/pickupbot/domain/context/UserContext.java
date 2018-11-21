package com.phoeniksoft.pickupbot.domain.context;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;

@Builder
@Getter
public class UserContext {

    @NonNull
    private AdviceType userIntent;

    private UserAnswer userAnswer;

    private final ContextPayload payload = new ContextPayload();

    public final class ContextPayload extends HashMap<String, Object> {

        public static final String PREV_ADVICE_PARAM = "prevAdviceId";
    }
}
