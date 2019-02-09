package com.phoeniksoft.pickupbot.app.config;

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.domain.advisor.Advisor;
import com.phoeniksoft.pickupbot.domain.advisor.BinaryAdvisor;
import com.phoeniksoft.pickupbot.domain.advisor.MessageStore;
import com.phoeniksoft.pickupbot.domain.context.ContextFiller;
import com.phoeniksoft.pickupbot.domain.core.PickupBotApi;
import com.phoeniksoft.pickupbot.domain.core.PickupBotFacadeImpl;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import com.phoeniksoft.pickupbot.domain.history.UserAnswersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

    @Bean
    public PickupBotApi pickupBotApi(ContextFiller contextFiller,
                                     Advisor advisor,
                                     HistoryService historyService,
                                     UserAnswersService userAnswersService) {
        return new PickupBotFacadeImpl(contextFiller, advisor, historyService, userAnswersService);
    }

    @Bean
    public Advisor advisor(AdviceStore adviceStore, MessageStore messageStore) {
        return new BinaryAdvisor(adviceStore, messageStore);
    }

}
