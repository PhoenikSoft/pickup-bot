package com.phoeniksoft.pickupbot.infrastructure.jpa.notification;

import com.phoeniksoft.pickupbot.domain.core.user.User;
import com.phoeniksoft.pickupbot.domain.notification.Subscription;
import com.phoeniksoft.pickupbot.domain.notification.SubscriptionService;
import com.phoeniksoft.pickupbot.domain.notification.Topic;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaSubscriptionService implements SubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserRepository userRepository;

    @Override
    public Subscription subscribe(User user, Topic topic) {
        UserDto userDto = userRepository.findByTelegramId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("There is no user with such telegram id for save"));
        return this.userSubscriptionRepository.save(UserSubscriptionDto.of(userDto, topic)).toSubscription();
    }
}
