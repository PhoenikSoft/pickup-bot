package com.phoeniksoft.pickupbot.infrastructure.jpa;

import com.phoeniksoft.pickupbot.domain.core.user.User;
import com.phoeniksoft.pickupbot.domain.core.user.UserStore;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class JpaUserStore implements UserStore {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(UUID.fromString(id)).map(UserDto::toUser);
    }
}
