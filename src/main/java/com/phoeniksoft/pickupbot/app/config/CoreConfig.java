package com.phoeniksoft.pickupbot.app.config;

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.domain.advisor.Advisor;
import com.phoeniksoft.pickupbot.domain.advisor.BinaryAdvisor;
import com.phoeniksoft.pickupbot.domain.context.ContextFiller;
import com.phoeniksoft.pickupbot.domain.core.PickupBotApi;
import com.phoeniksoft.pickupbot.domain.core.PickupBotFacadeImpl;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

    @Bean
    public PickupBotApi pickupBotApi(ContextFiller contextFiller, Advisor advisor, HistoryService historyService) {
        return new PickupBotFacadeImpl(contextFiller, advisor, historyService);
    }

    @Bean
    public Advisor advisor(AdviceStore adviceStore) {
        return new BinaryAdvisor(adviceStore);
    }

}
