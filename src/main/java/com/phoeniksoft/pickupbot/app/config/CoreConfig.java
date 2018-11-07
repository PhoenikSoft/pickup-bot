package com.phoeniksoft.pickupbot.app.config;

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.domain.advisor.InMemoryAdviceStore;
import com.phoeniksoft.pickupbot.domain.core.PickupBotApi;
import com.phoeniksoft.pickupbot.domain.core.SimplePickupBotImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

    @Bean
    public AdviceStore adviceStore() {
        return new InMemoryAdviceStore();
    }

    @Bean
    public PickupBotApi pickupBotApi(AdviceStore<String> adviceStore) {
        return new SimplePickupBotImpl(adviceStore);
    }

}
