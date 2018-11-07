package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Neo4jAdviceDao extends Neo4jRepository<AdviceDto, Long> {

    Optional<AdviceDto> findByTitle(@Param("title") String title);
}
