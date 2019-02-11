package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface Neo4jAdviceRepository extends Neo4jRepository<AdviceDto, Long> {

    @Query("MATCH (n:Advice) WHERE any(tag IN n.tags WHERE tag = {0}) RETURN n")
    List<AdviceDto> getNodesByTag(String tag);

    @Query("MATCH (n:Advice) WHERE any(tag IN n.tags WHERE tag = {0}) AND NOT id(n) IN {1} RETURN n")
    List<AdviceDto> getNodesByTagThatWereNotUsed(String tag, Collection<Long> ids);

    @Query("MATCH (e:Advice)-[r:goto]->(dest) WHERE id(e) = {0} AND r.helped = {1} RETURN dest")
    Optional<AdviceDto> getNextAdviceByUserAnswer(Long prevNodeId, String userAnswer);

    @Query("MATCH (e:Advice:Default) RETURN e LIMIT 1")
    AdviceDto getDefaultNode();
}
