package com.phoeniksoft.pickupbot.app.config;

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.domain.advisor.Advisor;
import com.phoeniksoft.pickupbot.domain.advisor.BinaryAdvisor;
import com.phoeniksoft.pickupbot.domain.core.PickupBotApi;
import com.phoeniksoft.pickupbot.domain.core.SimplePickupBotImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

    @Bean
    public PickupBotApi pickupBotApi(AdviceStore adviceStore) {
        return new SimplePickupBotImpl(adviceStore);
    }

    @Bean
    public Advisor advisor(AdviceStore adviceStore) {
        return new BinaryAdvisor(adviceStore);
    }

}
