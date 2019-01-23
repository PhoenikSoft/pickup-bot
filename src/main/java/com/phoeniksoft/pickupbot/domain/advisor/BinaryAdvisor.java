package com.phoeniksoft.pickupbot.domain.advisor;

import com.phoeniksoft.pickupbot.domain.context.UserContext;
import lombok.AllArgsConstructor;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
public class BinaryAdvisor implements Advisor {

    private final AdviceStore adviceStore;

    @Override
    public Advice getAdvice(UserContext context) {
        Advice adviceForUser;
        switch (context.getUserIntent()) {
            case START_MESSAGE:
                adviceForUser = getBeginAdvice(context);
                break;
            case NEXT_STEP:
                adviceForUser = getNextAdvice(context);
                break;
            default:
                adviceForUser = getDefaultAdvice();
                break;
        }
        return adviceForUser;
    }

    private Advice getBeginAdvice(UserContext context) {
        return adviceStore.getStartAdviceForUser(context.getUser().getId());
    }

    private Advice getNextAdvice(UserContext context) {
        requireNonNull(context.getUserAnswer());
        Object prevAdviceId = context.getPayload().get(UserContext.ContextPayload.PREV_ADVICE_PARAM);
        if (prevAdviceId == null) {
            return getBeginAdvice(context);
        }

        NextAdviceParams params = new NextAdviceParams();
        params.put(NextAdviceParams.USER_ANSWER_PARAM, context.getUserAnswer());
        return adviceStore.getNextAdvice(prevAdviceId.toString(), params).orElse(getDefaultAdvice());
    }

    private Advice getDefaultAdvice() {
        return adviceStore.getDefaultAdvice();
    }

}
