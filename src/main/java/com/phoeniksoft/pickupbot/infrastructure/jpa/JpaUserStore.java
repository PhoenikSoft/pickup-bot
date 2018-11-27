package com.phoeniksoft.pickupbot.infrastructure.jpa;

import com.phoeniksoft.pickupbot.domain.core.user.User;
import com.phoeniksoft.pickupbot.domain.core.user.UserStore;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class JpaUserStore implements UserStore {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findByTelegramId(id).map(UserDto::toUser);
    }

    @Override
    public User save(User user) {
        return userRepository.save(UserDto.of(user)).toUser();
    }
}
