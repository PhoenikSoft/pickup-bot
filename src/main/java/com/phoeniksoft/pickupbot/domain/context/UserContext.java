package com.phoeniksoft.pickupbot.domain.context;

import com.phoeniksoft.pickupbot.domain.core.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
public class UserContext {

    private AdviceGoal userIntent;

    private UserAnswer userAnswer;

    private User user;

    private ContextPayload payload = new ContextPayload();

    public final class ContextPayload extends HashMap<String, Object> {

        public static final String PREV_ADVICE_PARAM = "prevAdviceId";
    }
}
