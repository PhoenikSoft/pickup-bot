package com.phoeniksoft.pickupbot.domain.core;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.advisor.Advisor;
import com.phoeniksoft.pickupbot.domain.context.ContextFiller;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PickupBotFacadeImpl implements PickupBotApi {

    private final ContextFiller contextFiller;
    private final Advisor advisor;
    private final HistoryService historyService;

    @Override
    public UserAdvice getAdvice(UserQuery userQuery) {
        UserContext userContext = contextFiller.fillContext(userQuery);
        Advice advice = advisor.getAdvice(userContext);
        if (userContext.getUser() != null) {
            historyService.saveHistory(userContext.getUser(), advice);
        }
        return UserAdvice.of(advice);
    }
}
