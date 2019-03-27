package com.phoeniksoft.pickupbot.infrastructure.jpa.notification;

import com.phoeniksoft.pickupbot.domain.notification.Subscription;
import com.phoeniksoft.pickupbot.domain.notification.Topic;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_subscriptions")
@ToString(exclude = "user")
public class UserSubscriptionDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserDto user;

    @Enumerated(EnumType.STRING)
    @Column(name = "topic")
    private Topic topic;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    public Subscription toSubscription() {
        return Subscription.of(user.toTelegramUser(), topic);
    }

    static UserSubscriptionDto of(UserDto user, Topic topic) {
        UserSubscriptionDto dto = new UserSubscriptionDto();
        dto.user = user;
        dto.topic = topic;
        return dto;
    }
}
