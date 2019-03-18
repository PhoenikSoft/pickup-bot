package com.phoeniksoft.pickupbot.domain.history;

import com.phoeniksoft.pickupbot.domain.core.user.User;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * Represents history logging record.
 */
@Value
@Builder
public class UserHistory {

    Long id;
    User user;
    String adviceId;
    LocalDateTime created;
}
