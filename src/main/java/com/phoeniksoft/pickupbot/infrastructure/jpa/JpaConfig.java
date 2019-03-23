package com.phoeniksoft.pickupbot.infrastructure.jpa;

import com.phoeniksoft.pickupbot.domain.core.user.UserStore;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import com.phoeniksoft.pickupbot.domain.history.UserAnswersService;
import com.phoeniksoft.pickupbot.domain.notification.SubscriptionService;
import com.phoeniksoft.pickupbot.infrastructure.jpa.answer.JpaUserAnswersService;
import com.phoeniksoft.pickupbot.infrastructure.jpa.answer.UserAnswersRepository;
import com.phoeniksoft.pickupbot.infrastructure.jpa.history.JpaHistoryService;
import com.phoeniksoft.pickupbot.infrastructure.jpa.history.UserHistoryRepository;
import com.phoeniksoft.pickupbot.infrastructure.jpa.notification.JpaSubscriptionService;
import com.phoeniksoft.pickupbot.infrastructure.jpa.notification.UserSubscriptionRepository;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.JpaUserStore;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Bean configuration of the JPA and Postgres database connection.
 */
@Configuration
@EnableJpaRepositories(transactionManagerRef = "postgreTransactionManager")
@Slf4j
@Profile("!dev")
public class JpaConfig {

    @Bean
    public UserStore userStore(UserRepository userRepository) {
        return new JpaUserStore(userRepository);
    }

    @Bean
    public HistoryService historyService(UserHistoryRepository userHistoryRepository, UserRepository userRepository) {
        return new JpaHistoryService(userHistoryRepository, userRepository);
    }

    @Bean
    public UserAnswersService userAnswersService(UserAnswersRepository userAnswersRepository,
                                                 UserRepository userRepository,
                                                 UserHistoryRepository userHistoryRepository) {
        return new JpaUserAnswersService(userAnswersRepository, userRepository, userHistoryRepository);
    }

    @Bean
    public SubscriptionService subscriptionService(UserSubscriptionRepository userSubscriptionRepository,
                                                   UserRepository userRepository) {
        return new JpaSubscriptionService(userSubscriptionRepository, userRepository);
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.phoeniksoft.pickupbot.infrastructure.jpa")
                .build();
    }

    @Primary
    @Bean(name = "postgreTransactionManager")
    public JpaTransactionManager postgreTransactionManager(
            @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

    @Autowired
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(Neo4jTransactionManager neo4jTransactionManager,
                                                         JpaTransactionManager mysqlTransactionManager) {
        log.debug("Initializing platform transaction manager");
        return new ChainedTransactionManager(mysqlTransactionManager, neo4jTransactionManager);
    }
}
