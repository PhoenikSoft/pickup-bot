package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.UserAnswer;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.context.interceptors.exception.CannotRecognizeUserAnswerException;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;

public class UserAnswerInterceptor implements ContextInterceptor {

    @Override
    public void fillContext(UserContext context, UserQuery userQuery) throws CannotRecognizeUserAnswerException {
        if (userQuery.getMessage() != null) {
            UserAnswer userAnswer;
            try {
                userAnswer = UserAnswer.valueOf(userQuery.getMessage().getValue().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new CannotRecognizeUserAnswerException("Cannot recognize user answer", ex);
            }
            context.setUserAnswer(userAnswer);
        }
    }
}
