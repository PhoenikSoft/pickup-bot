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
        checkSubscriptionForDuplication(userDto, topic);
        return userSubscriptionRepository.save(UserSubscriptionDto.of(userDto, topic)).toSubscription();
    }

    private void checkSubscriptionForDuplication(UserDto userDto, Topic topic) {
        if (userSubscriptionRepository.existsByUserAndTopic(userDto, topic)) {
            throw new UserWithTopicDuplicateException(
                    String.format("Cannot save user subscription, because there is already user [%s] with topic [%S] in DB",
                            userDto.getId(), topic));
        }
    }
}
