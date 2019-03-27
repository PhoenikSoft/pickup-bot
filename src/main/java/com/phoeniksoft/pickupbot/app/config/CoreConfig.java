package com.phoeniksoft.pickupbot.app.config;

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.domain.advisor.Advisor;
import com.phoeniksoft.pickupbot.domain.advisor.AdvisorImpl;
import com.phoeniksoft.pickupbot.domain.context.ContextFiller;
import com.phoeniksoft.pickupbot.domain.core.PickupBotApi;
import com.phoeniksoft.pickupbot.domain.core.PickupBotFacadeImpl;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import com.phoeniksoft.pickupbot.domain.history.UserAnswersService;
import com.phoeniksoft.pickupbot.domain.notification.SubscriptionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean configuration of the core domain module.
 */
@Configuration
public class CoreConfig {

    @Bean
    public PickupBotApi pickupBotApi(ContextFiller contextFiller,
                                     Advisor advisor,
                                     HistoryService historyService,
                                     UserAnswersService userAnswersService,
                                     SubscriptionService subscriptionService) {
        return new PickupBotFacadeImpl(contextFiller, advisor, historyService, userAnswersService, subscriptionService);
    }

    @Bean
    public Advisor advisor(AdviceStore adviceStore) {
        return new AdvisorImpl(adviceStore);
    }

}
