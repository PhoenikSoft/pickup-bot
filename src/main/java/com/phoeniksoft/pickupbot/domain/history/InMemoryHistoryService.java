package com.phoeniksoft.pickupbot.domain.history;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.core.user.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class InMemoryHistoryService implements HistoryService {

    @Override
    public UserHistory saveHistory(User user, Advice advice) {
        return new UserHistory(1L, new User("test"), "1", LocalDateTime.now());
    }

    @Override
    public Optional<String> getLastAdviceId(User user) {
        return Optional.empty();
    }

    @Override
    public Set<String> getPastAdvicesIds(User user) {
        return new HashSet<>();
    }
}
