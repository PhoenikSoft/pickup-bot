package com.phoeniksoft.pickupbot.domain.history;

import com.phoeniksoft.pickupbot.domain.core.user.User;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class UserAnswerHistory {

    Long id;
    String answer;
    User user;
    String adviceId;
    LocalDateTime created;
}
