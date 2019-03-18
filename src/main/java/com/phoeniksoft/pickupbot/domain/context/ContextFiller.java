package com.phoeniksoft.pickupbot.domain.context;

import com.phoeniksoft.pickupbot.domain.core.UserQuery;

/**
 * Service that fills context with information that it can infer from the user query.
 */
public interface ContextFiller {

    UserContext fillContext(UserQuery userQuery);
}
