package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

@Configuration
@ComponentScan
@EnableNeo4jRepositories(
        sessionFactoryRef = "getSessionFactory",
        transactionManagerRef = "graphTransactionManager")
public class Neo4jConfig {

    @Bean
    public AdviceStore adviceStore(Neo4jAdviceRepository repository) {
        return new Neo4jAdviceStore(repository);
    }

    @Bean(name = "getSessionFactory")
    public SessionFactory graphSessionFactory(org.neo4j.ogm.config.Configuration configuration) {
        return new SessionFactory(configuration, "com.phoeniksoft.pickupbot.infrastructure.neo4j");
    }

    @Bean(name = "graphTransactionManager")
    public Neo4jTransactionManager graphTransactionManager(
            @Qualifier("getSessionFactory") SessionFactory sessionFactory) {
        return new Neo4jTransactionManager(sessionFactory);
    }

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        String graphenedbURL = System.getenv("GRAPHENEDB_BOLT_URL");
        String graphenedbUser = System.getenv("GRAPHENEDB_BOLT_USER");
        String graphenedbPass = System.getenv("GRAPHENEDB_BOLT_PASSWORD");
        return new org.neo4j.ogm.config.Configuration.Builder()
                .uri(graphenedbURL)
                .credentials(graphenedbUser, graphenedbPass)
                .build();
    }
}
