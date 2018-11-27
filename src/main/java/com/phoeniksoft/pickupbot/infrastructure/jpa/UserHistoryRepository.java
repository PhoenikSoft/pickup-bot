package com.phoeniksoft.pickupbot.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistoryDto, Long> {

    Optional<UserHistoryDto> findFirstByUserOrderByCreatedDesc(UserDto user);
}
