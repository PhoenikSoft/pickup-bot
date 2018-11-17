package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;

public interface ContextInterceptor {

    void fillContext(UserContext context, UserQuery userQuery);
}
