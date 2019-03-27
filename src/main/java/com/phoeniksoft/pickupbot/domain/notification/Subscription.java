package com.phoeniksoft.pickupbot.domain.notification;

import com.phoeniksoft.pickupbot.domain.core.user.User;
import lombok.Value;

@Value(staticConstructor = "of")
public class Subscription {

    private User user;
    private Topic topic;
}
