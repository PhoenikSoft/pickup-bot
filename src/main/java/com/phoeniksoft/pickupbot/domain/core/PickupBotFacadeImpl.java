package com.phoeniksoft.pickupbot.domain.core;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.advisor.Advisor;
import com.phoeniksoft.pickupbot.domain.context.ContextFiller;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import com.phoeniksoft.pickupbot.domain.history.UserAnswersService;
import com.phoeniksoft.pickupbot.domain.notification.SubscriptionService;
import com.phoeniksoft.pickupbot.domain.notification.Topic;
import lombok.AllArgsConstructor;

import static com.phoeniksoft.pickupbot.domain.context.UserContext.ContextPayload.TOPIC_PARAM;

/**
 * Service that delegates complex advice work to other components.
 */
@AllArgsConstructor
public class PickupBotFacadeImpl implements PickupBotApi {

    private final ContextFiller contextFiller;
    private final Advisor advisor;
    private final HistoryService historyService;
    private final UserAnswersService userAnswersService;
    private final SubscriptionService subscriptionService;

    @Override
    public UserAdvice getAdvice(UserQuery userQuery) {
        UserContext userContext = contextFiller.fillContext(userQuery);
        Advice advice = advisor.getAdvice(userContext);
        if (userContext.getUser() != null) {
            historyService.saveHistory(userContext.getUser(), advice);
        }
        return UserAdvice.of(advice);
    }

    @Override
    public void saveUserAnswer(UserQuery userQuery) {
        UserContext userContext = contextFiller.fillContext(userQuery);
        userAnswersService.saveAnswer(userContext);
    }

    @Override
    public void subscribe(UserQuery userQuery) {
        UserContext userContext = contextFiller.fillContext(userQuery);
        subscriptionService.subscribe(userContext.getUser(), (Topic) userContext.getPayload().get(TOPIC_PARAM));
    }
}
