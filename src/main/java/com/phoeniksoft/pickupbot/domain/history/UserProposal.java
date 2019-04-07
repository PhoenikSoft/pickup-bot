package com.phoeniksoft.pickupbot.domain.history;

import com.phoeniksoft.pickupbot.domain.core.user.User;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * Represents proposal by user (pickup message, feedback).
 */
@Value
@Builder
public class UserProposal {

    Long id;
    String message;
    User user;
    LocalDateTime created;
}
