package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.AdviceType;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;

public class UserCommandInterceptor implements ContextInterceptor {

    @Override
    public void fillContext(UserContext context, UserQuery userQuery) {
        AdviceType intent;
        switch (userQuery.getCommand()) {
            case GET_START_ADVICE:
                intent = AdviceType.START_MESSAGE;
                break;
            case GET_NEXT_ADVICE:
                intent = AdviceType.NEXT_STEP;
                break;
            case FINISH_ADVICE:
                intent = AdviceType.DATE_INVITATION;
                break;
            default:
                intent = AdviceType.START_MESSAGE;
                break;
        }

        context.setUserIntent(intent);
    }
}
