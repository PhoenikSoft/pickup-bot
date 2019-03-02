package com.phoeniksoft.pickupbot.domain.core.user;

import java.util.List;
import java.util.Optional;

public interface UserStore {

    Optional<User> findById(String id);

    List<User> getAll();

    User save(User user);
}
