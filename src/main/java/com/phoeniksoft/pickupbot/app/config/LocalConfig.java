package com.phoeniksoft.pickupbot.app.config;

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.domain.advisor.InMemoryAdviceStore;
import com.phoeniksoft.pickupbot.domain.core.user.InMemoryUserStore;
import com.phoeniksoft.pickupbot.domain.core.user.UserStore;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import com.phoeniksoft.pickupbot.domain.history.InMemoryHistoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Testing bean config. Use it if you want to test application and avoid infrastructure layer dependencies.
 */
@Configuration
@Profile("dev")
public class LocalConfig {

    @Bean
    public AdviceStore adviceStore() {
        return new InMemoryAdviceStore();
    }

    @Bean
    public UserStore userStore() {
        return new InMemoryUserStore();
    }

    @Bean
    public HistoryService historyService() {
        return new InMemoryHistoryService();
    }
}
