package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface Neo4jMessageRepository extends Neo4jRepository<MessageDto, Long> {

    @Query("MATCH (e:BEGIN) RETURN e")
    List<MessageDto> getAllStartNodes();

    @Query("MATCH (n:BEGIN) WHERE NOT ID(n) IN {0} RETURN n")
    List<MessageDto> getStartNodesThatWereNotUsed(Collection<Long> ids);

    @Query("MATCH (e:Default) RETURN e LIMIT 1")
    MessageDto getDefaultNode();
}
