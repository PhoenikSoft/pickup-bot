package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.UserAnswer;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.context.interceptors.exception.CannotRecognizeUserAnswerException;
import com.phoeniksoft.pickupbot.domain.core.UserCommand;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;

public class UserAnswerInterceptor implements ContextInterceptor {

    @Override
    public void fillContext(UserContext context, UserQuery userQuery) throws CannotRecognizeUserAnswerException {
        UserAnswer userAnswer;
        try {
            userAnswer = UserAnswer.valueOf(userQuery.getMessage().getValue().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new CannotRecognizeUserAnswerException("Cannot recognize user answer", ex);
        }
        context.setUserAnswer(userAnswer);
    }

    @Override
    public boolean isAcceptable(UserContext context, UserQuery userQuery) {
        return userQuery.getCommand() != null &&
                userQuery.getCommand() != UserCommand.GET_START_ADVICE &&
                userQuery.getMessage() != null;
    }
}
