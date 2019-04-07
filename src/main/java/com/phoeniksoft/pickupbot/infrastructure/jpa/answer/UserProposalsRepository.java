package com.phoeniksoft.pickupbot.infrastructure.jpa.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProposalsRepository extends JpaRepository<UserProposalDto, Long> {
}
