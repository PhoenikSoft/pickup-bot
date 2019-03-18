package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.UserAnswer;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.context.interceptors.exception.CannotRecognizeUserAnswerException;
import com.phoeniksoft.pickupbot.domain.core.UserCommand;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;

import java.util.Arrays;
import java.util.List;

/**
 * Identifies answer given by user if possible.
 */
public class UserAnswerInterceptor implements ContextInterceptor {

    private static final List<UserCommand> ACCEPTABLE_COMMANDS = Arrays.asList(UserCommand.GET_NEXT_ADVICE, UserCommand.RATE_ADVICE);

    @Override
    public void fillContext(UserContext context, UserQuery userQuery) throws CannotRecognizeUserAnswerException {
        UserAnswer userAnswer;
        try {
            userAnswer = UserAnswer.valueOf(userQuery.getMessage().getValue());
        } catch (IllegalArgumentException ex) {
            throw new CannotRecognizeUserAnswerException("Cannot recognize user answer", ex);
        }
        context.setUserAnswer(userAnswer);
    }

    @Override
    public boolean isAcceptable(UserContext context, UserQuery userQuery) {
        return userQuery.getCommand() != null &&
                ACCEPTABLE_COMMANDS.contains(userQuery.getCommand()) &&
                userQuery.getMessage() != null;
    }
}
