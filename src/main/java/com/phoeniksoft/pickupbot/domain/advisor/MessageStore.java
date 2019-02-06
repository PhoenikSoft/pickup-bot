package com.phoeniksoft.pickupbot.domain.advisor;

import com.phoeniksoft.pickupbot.domain.core.user.User;

public interface MessageStore {

    Advice getStartMessageForUser(User user);
}
