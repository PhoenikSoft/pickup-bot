package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.UserAnswer;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;

public class UserAnswerInterceptor implements ContextInterceptor {

    @Override
    public void fillContext(UserContext context, UserQuery userQuery) {
        UserAnswer userAnswer = UserAnswer.valueOf(userQuery.getMessage().getValue());
        context.setUserAnswer(userAnswer);
    }
}
