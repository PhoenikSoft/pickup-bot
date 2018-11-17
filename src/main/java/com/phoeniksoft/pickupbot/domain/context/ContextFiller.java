package com.phoeniksoft.pickupbot.domain.context;

import com.phoeniksoft.pickupbot.domain.core.UserQuery;

public interface ContextFiller {

    UserContext fillContext(UserQuery userQuery);
}
