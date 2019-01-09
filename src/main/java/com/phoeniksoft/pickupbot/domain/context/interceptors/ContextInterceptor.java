package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;

public interface ContextInterceptor {

    int DEFAULT_PRIORITY = 0;
    int HIGH_PRIORITY = DEFAULT_PRIORITY + 100;

    void fillContext(UserContext context, UserQuery userQuery);

    default boolean isAcceptable(UserContext context, UserQuery userQuery) {
        return true;
    }

    default int priority() {
        return DEFAULT_PRIORITY;
    }
}
