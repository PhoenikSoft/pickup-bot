package com.phoeniksoft.pickupbot.domain.core.user;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InMemoryUserStore implements UserStore {

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return Arrays.asList(new User("test"));
    }

    @Override
    public User save(User user) {
        return new User("test");
    }
}
