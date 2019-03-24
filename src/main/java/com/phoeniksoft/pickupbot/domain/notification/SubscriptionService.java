package com.phoeniksoft.pickupbot.domain.notification;

import com.phoeniksoft.pickupbot.domain.core.user.User;

import java.util.List;

public interface SubscriptionService {

    Subscription subscribe(User user, Topic topic);

    List<User> getUsersSubscribedToTopic(Topic topic);
}
