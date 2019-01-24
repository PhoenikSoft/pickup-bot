package com.phoeniksoft.pickupbot.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserAnswersRepository extends JpaRepository<UserAnswerDto, Long> {
}
