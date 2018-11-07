package com.phoeniksoft.pickupbot.domain.core;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimplePickupBotImpl implements PickupBotApi {

    private final AdviceStore<String> adviceStore;

    @Override
    public UserAdvice getAdvice(UserQuery userQuery) {
        Advice response;
        switch (userQuery.getCommand()) {
            case GET_START_ADVICE:
                response = adviceStore.getStartAdvice();
                break;
            case GET_NEXT_ADVICE:
                Object prevAdviceId = userQuery.getSpecificParams().get("prevAdviceId");
                response = adviceStore.getById(prevAdviceId.toString())
                        .getNextAdvice()
                        .orElse(adviceStore.getDefaultAdvice());
                break;
            default:
                response = adviceStore.getDefaultAdvice();
                break;
        }
        return UserAdvice.of(response);
    }
}
