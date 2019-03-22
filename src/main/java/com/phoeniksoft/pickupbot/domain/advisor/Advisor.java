package com.phoeniksoft.pickupbot.domain.advisor;

import com.phoeniksoft.pickupbot.domain.context.UserContext;

/**
 * The service that analyzes user context and determines with advice to give next.
 */
public interface Advisor {

    /**
     * Returns next advice by context.
     */
    Advice getAdvice(UserContext context);
}
