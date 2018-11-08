package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@EnableNeo4jRepositories(basePackageClasses = Neo4jMarker.class)
public class Neo4jConfig {

    @Bean
    public AdviceStore adviceStore(Neo4jAdviceRepository repository) {
        return new Neo4jAdviceStore(repository);
    }
}
