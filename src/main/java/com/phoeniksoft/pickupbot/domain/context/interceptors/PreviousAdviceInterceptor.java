package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import lombok.AllArgsConstructor;

import java.util.Optional;

import static com.phoeniksoft.pickupbot.domain.context.UserContext.ContextPayload.PREV_ADVICE_PARAM;

@AllArgsConstructor
public class PreviousAdviceInterceptor implements ContextInterceptor {

    private final HistoryService historyService;

    @Override
    public void fillContext(UserContext context, UserQuery userQuery) {
        if (context.getUser() != null) {
            Optional<String> lastAdviceId = historyService.getLastAdviceId(context.getUser());
            if (lastAdviceId.isPresent()) {
                context.getPayload().put(PREV_ADVICE_PARAM, lastAdviceId.get());
            }
        }
    }
}
