package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserCommand;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;
import com.phoeniksoft.pickupbot.domain.core.UserQueryParams;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.phoeniksoft.pickupbot.domain.context.UserContext.ContextPayload.PREV_ADVICE_PARAM;

@AllArgsConstructor
public class PreviousAdviceInterceptor implements ContextInterceptor {

    private static final List<UserCommand> ACCEPTABLE_COMMANDS = Arrays.asList(UserCommand.GET_NEXT_ADVICE, UserCommand.RATE_ADVICE);

    private final HistoryService historyService;

    @Override
    public void fillContext(UserContext context, UserQuery userQuery) {
        if (userQuery.getSpecificParams().containsKey(UserQueryParams.ADVICE_ID_PARAM)) {
            context.getPayload().put(PREV_ADVICE_PARAM, userQuery.getSpecificParams().get(UserQueryParams.ADVICE_ID_PARAM));
            return;
        }

        Optional<String> lastAdviceId = historyService.getLastAdviceId(context.getUser());
        if (lastAdviceId.isPresent()) {
            context.getPayload().put(PREV_ADVICE_PARAM, lastAdviceId.get());
        }
    }

    @Override
    public boolean isAcceptable(UserContext context, UserQuery userQuery) {
        return userQuery.getCommand() != null &&
                ACCEPTABLE_COMMANDS.contains(userQuery.getCommand()) &&
                context.getUser() != null;
    }
}
