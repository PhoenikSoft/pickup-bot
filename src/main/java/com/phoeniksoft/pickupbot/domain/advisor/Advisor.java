package com.phoeniksoft.pickupbot.domain.advisor;

import com.phoeniksoft.pickupbot.domain.context.UserContext;

public interface Advisor {

    Advice getAdvice(UserContext context);
}
