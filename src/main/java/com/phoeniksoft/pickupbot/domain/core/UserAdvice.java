package com.phoeniksoft.pickupbot.domain.core;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public final class UserAdvice {

    private final String msg;

    private final Map<String, Object> payload = new HashMap<>();

    public static UserAdvice of(Advice advice) {
        UserAdvice userAdvice = new UserAdvice(advice.getMsg());
        userAdvice.getPayload().put("adviceId", advice.getId());
        return userAdvice;
    }
}
