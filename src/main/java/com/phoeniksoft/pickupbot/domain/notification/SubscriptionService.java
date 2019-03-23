package com.phoeniksoft.pickupbot.domain.notification;

import com.phoeniksoft.pickupbot.domain.core.user.User;

public interface SubscriptionService {

    Subscription subscribe(User user, Topic topic);
}
