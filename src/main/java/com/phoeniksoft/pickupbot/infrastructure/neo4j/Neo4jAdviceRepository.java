package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Neo4jAdviceRepository extends Neo4jRepository<AdviceDto, Long> {

    @Query("MATCH (e:Advice)-[r:goto]->(dest) WHERE id(e) = {0} AND r.helped = {1} RETURN dest")
    Optional<AdviceDto> getNextAdviceByUserAnswer(Long prevNodeId, String userAnswer);

    @Query("MATCH (e:BEGIN) RETURN e")
    List<AdviceDto> getAllStartNodes();

    @Query("MATCH (e:Default) RETURN e LIMIT 1")
    AdviceDto getDefaultNode();
}
