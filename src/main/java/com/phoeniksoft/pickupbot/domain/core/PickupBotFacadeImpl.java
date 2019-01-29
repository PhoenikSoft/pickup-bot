package com.phoeniksoft.pickupbot.domain.core;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.advisor.Advisor;
import com.phoeniksoft.pickupbot.domain.context.ContextFiller;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import com.phoeniksoft.pickupbot.domain.history.UserAnswersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;

public class PickupBotFacadeImpl implements PickupBotApi {

    private final ContextFiller contextFiller;
    private final Advisor advisor;
    private final HistoryService historyService;
    private final UserAnswersService userAnswersService;

    @Autowired
    public PickupBotFacadeImpl(ContextFiller contextFiller,
                               Advisor advisor,
                               HistoryService historyService,
                               UserAnswersService userAnswersService) {
        this.contextFiller = contextFiller;
        this.advisor = advisor;
        this.historyService = historyService;
        this.userAnswersService = userAnswersService;
    }

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
}
