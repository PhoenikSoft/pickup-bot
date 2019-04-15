package com.phoeniksoft.pickupbot.infrastructure.jpa.answer;

import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProposalsRepository extends JpaRepository<UserProposalDto, Long> {

    long countByUser(UserDto user);
}
