package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.domain.advisor.MessageStore;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

import java.util.Random;

@Configuration
@ComponentScan
@EnableNeo4jRepositories(
        sessionFactoryRef = "getSessionFactory",
        transactionManagerRef = "graphTransactionManager")
@Profile("!dev")
public class Neo4jConfig {

    @Value("${GRAPHENEDB_BOLT_URL:bolt://localhost:7687}")
    private String graphenedbURL;

    @Value("${GRAPHENEDB_BOLT_USER:neo4j}")
    private String graphenedbUser;

    @Value("${GRAPHENEDB_BOLT_PASSWORD:admin}")
    private String graphenedbPass;

    @Bean
    public AdviceStore adviceStore(Neo4jAdviceRepository repository, HistoryService historyService) {
        return new Neo4jAdviceStore(repository, historyService, new Random());
    }

    @Bean
    public MessageStore messageStore(Neo4jMessageRepository repository, HistoryService historyService) {
        return new Neo4jMessageStore(repository, historyService, new Random());
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
        return new org.neo4j.ogm.config.Configuration.Builder()
                .uri(graphenedbURL)
                .credentials(graphenedbUser, graphenedbPass)
                .build();
    }
}
