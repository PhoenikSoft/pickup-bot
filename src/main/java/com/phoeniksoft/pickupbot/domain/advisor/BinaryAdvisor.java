package com.phoeniksoft.pickupbot.domain.advisor;

import com.phoeniksoft.pickupbot.domain.advisor.exception.NoPrevAdviceFoundException;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import lombok.AllArgsConstructor;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
public class BinaryAdvisor implements Advisor {

    private final AdviceStore adviceStore;
    private final MessageStore messageStore;

    @Override
    public Advice getAdvice(UserContext context) {
        Advice adviceForUser;
        switch (context.getUserIntent()) {
            case START_MESSAGE:
                adviceForUser = getBeginAdvice(context);
                break;
            case NEXT_ADVICE:
                adviceForUser = getNextAdvice(context);
                break;
            case DATE_ADVICE:
                adviceForUser = getDateAdvice(context);
                break;
            case PROFILE_IMPROVEMENT:
                adviceForUser = getProfileAdvice(context);
                break;
            default:
                adviceForUser = getDefaultAdvice();
                break;
        }
        return adviceForUser;
    }

    private Advice getBeginAdvice(UserContext context) {
        return messageStore.getStartMessageForUser(context.getUser());
    }

    private Advice getDateAdvice(UserContext context) {
        return adviceStore.getAdviceByTypeForUser(AdviceType.DATE, context.getUser());
    }

    private Advice getProfileAdvice(UserContext context) {
        return adviceStore.getAdviceByTypeForUser(AdviceType.PROFILE, context.getUser());
    }

    private Advice getNextAdvice(UserContext context) {
        requireNonNull(context.getUserAnswer());
        Object prevAdviceId = context.getPayload().get(UserContext.ContextPayload.PREV_ADVICE_PARAM);
        if (prevAdviceId == null) {
            throw new NoPrevAdviceFoundException();
        }

        NextAdviceParams params = new NextAdviceParams();
        params.put(NextAdviceParams.USER_ANSWER_PARAM, context.getUserAnswer());
        return adviceStore.getNextAdvice(prevAdviceId.toString(), params).orElse(getDefaultAdvice());
    }

    private Advice getDefaultAdvice() {
        return adviceStore.getDefaultAdvice();
    }

}
