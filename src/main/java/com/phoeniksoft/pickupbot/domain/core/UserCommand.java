package com.phoeniksoft.pickupbot.domain.core;

import com.phoeniksoft.pickupbot.domain.context.AdviceGoal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserCommand {
    GET_START_ADVICE(AdviceGoal.START_MESSAGE),
    GET_NEXT_ADVICE(AdviceGoal.NEXT_ADVICE),
    GET_DATE_ADVICE(AdviceGoal.DATE_ADVICE),
    GET_PROFILE_ADVICE(AdviceGoal.PROFILE_IMPROVEMENT),
    RATE_ADVICE,
    ADD_USER_PROPOSAL,
    SUBSCRIBE_TO_TOPIC;

    private AdviceGoal adviceGoal;
}
