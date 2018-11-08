package com.phoeniksoft.pickupbot.domain.context;

import lombok.Data;

import java.util.Map;

@Data
public class UserContext {

    private AdviceType userIntent;

    private UserAnswer userAnswer;

    private Map<String, Object> payload;
}
