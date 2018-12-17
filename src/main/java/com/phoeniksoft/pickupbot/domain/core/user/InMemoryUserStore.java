package com.phoeniksoft.pickupbot.domain.core.user;

import java.util.Optional;

public class InMemoryUserStore implements UserStore {

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return new User("test");
    }
}
