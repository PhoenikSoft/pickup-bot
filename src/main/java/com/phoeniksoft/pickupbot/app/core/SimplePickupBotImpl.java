package com.phoeniksoft.pickupbot.app.core;

import com.phoeniksoft.pickupbot.app.PickupBotApi;
import com.phoeniksoft.pickupbot.app.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.model.Advice;
import com.phoeniksoft.pickupbot.model.UserAdvice;
import com.phoeniksoft.pickupbot.model.UserQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimplePickupBotImpl implements PickupBotApi {

    private final AdviceStore adviceStore;

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
