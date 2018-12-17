package com.phoeniksoft.pickupbot.domain.history;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.core.user.User;

import java.time.LocalDateTime;
import java.util.Optional;

public class InMemoryHistoryService implements HistoryService {

    @Override
    public UserHistory saveHistory(User user, Advice advice) {
        return new UserHistory(1L, new User("test"), "1", LocalDateTime.now());
    }

    @Override
    public Optional<String> getLastAdviceId(User user) {
        return Optional.empty();
    }
}
