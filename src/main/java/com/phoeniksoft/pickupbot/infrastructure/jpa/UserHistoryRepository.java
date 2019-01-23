package com.phoeniksoft.pickupbot.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistoryDto, Long> {

    Optional<UserHistoryDto> findFirstByUserOrderByCreatedDesc(UserDto user);

    @Query(value = "select distinct adviceId from UserHistoryDto where user = ?1")
    Set<Long> findDistinctAdviceIdsByUserId(UserDto user);
}
