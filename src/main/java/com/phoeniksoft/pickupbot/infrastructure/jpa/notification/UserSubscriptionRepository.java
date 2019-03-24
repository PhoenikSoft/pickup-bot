package com.phoeniksoft.pickupbot.infrastructure.jpa.notification;

import com.phoeniksoft.pickupbot.domain.notification.Topic;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptionDto, Long> {

    boolean existsByUserAndTopic(UserDto user, Topic topic);

    @Query("select s.user from UserSubscriptionDto s where s.topic = ?1")
    List<UserDto> getUsersSubscribedToTopic(Topic topic);
}
