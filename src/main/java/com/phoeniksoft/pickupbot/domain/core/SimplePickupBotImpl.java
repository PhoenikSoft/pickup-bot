package com.phoeniksoft.pickupbot.domain.core;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.domain.advisor.NextAdviceParams;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimplePickupBotImpl implements PickupBotApi {

    private final AdviceStore adviceStore;

    @Override
    public UserAdvice getAdvice(UserQuery userQuery) {
        Advice response;
        switch (userQuery.getCommand()) {
            case GET_NEXT_ADVICE:
                Object prevAdviceId = userQuery.getSpecificParams().get("prevAdviceId");
                response = adviceStore.getNextAdvice(prevAdviceId.toString(), new NextAdviceParams())
                        .orElse(adviceStore.getDefaultAdvice());
                break;
            default:
                response = adviceStore.getDefaultAdvice();
                break;
        }
        return UserAdvice.of(response);
    }

    @Override
    public void saveUserAnswer(UserQuery userQuery) {

    }
}
