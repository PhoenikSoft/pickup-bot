package com.phoeniksoft.pickupbot.infrastructure.jpa.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptionDto, Long> {
}
