package com.phoeniksoft.pickupbot.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserDto, UUID> {

    Optional<UserDto> findByTelegramId(String telegramId);
}
