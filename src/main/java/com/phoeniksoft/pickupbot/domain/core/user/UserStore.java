package com.phoeniksoft.pickupbot.domain.core.user;

import java.util.Optional;

public interface UserStore {

    Optional<User> findById(String id);
}
