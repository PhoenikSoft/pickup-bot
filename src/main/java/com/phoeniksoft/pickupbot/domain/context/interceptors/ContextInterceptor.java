package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;

/**
 * Interceptor that fills some peace of information to the core context.
 */
public interface ContextInterceptor {

    int DEFAULT_PRIORITY = 0;

    /**
     * Mark interceptor that should be executed firstly.
     */
    int HIGH_PRIORITY = DEFAULT_PRIORITY + 100;

    /**
     * Core method of the interceptor that fills context using already partially filled context and user query.
     *
     * @param context   - context to fill with information. Could be empty or filled by other interceptors
     * @param userQuery - query that was generated from user input
     */
    void fillContext(UserContext context, UserQuery userQuery);

    /**
     * Returns true if this interceptor should be applied and executed.
     */
    default boolean isAcceptable(UserContext context, UserQuery userQuery) {
        return true;
    }

    /**
     * Returns priority of the interceptor. Determines in which order interceptor should be executed.
     */
    default int priority() {
        return DEFAULT_PRIORITY;
    }
}
