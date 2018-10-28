package com.phoeniksoft.pickupbot.app;

import com.phoeniksoft.pickupbot.model.UserAdvice;
import com.phoeniksoft.pickupbot.model.UserQuery;

public interface PickupBotApi {

    UserAdvice getAdvice(UserQuery userQuery);
}
