package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.AdviceGoal;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserCommand;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;

import java.util.Optional;

/**
 * Identifies what is the intent of the user.
 */
public class UserCommandInterceptor implements ContextInterceptor {

    @Override
    public void fillContext(UserContext context, UserQuery userQuery) {
        AdviceGoal adviceGoal = Optional.ofNullable(userQuery.getCommand())
                .map(UserCommand::getAdviceGoal)
                .orElse(AdviceGoal.START_MESSAGE);
        context.setUserIntent(adviceGoal);
    }

    @Override
    public int priority() {
        return HIGH_PRIORITY;
    }
}
