package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserCommand;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;

import java.util.Arrays;
import java.util.List;

/**
 * Fills context with free text that was written by user and sent to bot.
 */
public class UserFreeTextInterceptor implements ContextInterceptor {

    private static final List<UserCommand> ACCEPTABLE_COMMANDS = Arrays.asList(UserCommand.ADD_USER_PROPOSAL);

    @Override
    public void fillContext(UserContext context, UserQuery userQuery) {
        context.setUserFreeText(userQuery.getMessage().getValue());
    }

    @Override
    public boolean isAcceptable(UserContext context, UserQuery userQuery) {
        return userQuery.getCommand() != null &&
                ACCEPTABLE_COMMANDS.contains(userQuery.getCommand()) &&
                userQuery.getMessage() != null;
    }
}
