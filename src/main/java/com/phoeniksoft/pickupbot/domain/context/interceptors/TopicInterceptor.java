package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserCommand;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;
import com.phoeniksoft.pickupbot.domain.core.UserQueryParams;
import com.phoeniksoft.pickupbot.domain.notification.Topic;

import java.util.Arrays;
import java.util.List;

import static com.phoeniksoft.pickupbot.domain.context.UserContext.ContextPayload.TOPIC_PARAM;

/**
 * Fills context with topic if possible.
 */
public class TopicInterceptor implements ContextInterceptor {

    private static final List<UserCommand> ACCEPTABLE_COMMANDS = Arrays.asList(UserCommand.SUBSCRIBE_TO_TOPIC);

    @Override
    public void fillContext(UserContext context, UserQuery userQuery) {
        if (userQuery.getSpecificParams().containsKey(UserQueryParams.TOPIC_PARAM)) {
            Object topic = userQuery.getSpecificParams().get(UserQueryParams.TOPIC_PARAM);
            if (!(topic instanceof Topic)) {
                throw new IllegalArgumentException("Topic param isn't of the correct type");
            }
            context.getPayload().put(TOPIC_PARAM, topic);
            return;
        }

        throw new IllegalStateException("Topic param is mandatory");
    }

    @Override
    public boolean isAcceptable(UserContext context, UserQuery userQuery) {
        return userQuery.getCommand() != null &&
                ACCEPTABLE_COMMANDS.contains(userQuery.getCommand());
    }
}
