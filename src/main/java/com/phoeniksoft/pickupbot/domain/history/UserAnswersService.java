package com.phoeniksoft.pickupbot.domain.history;

import com.phoeniksoft.pickupbot.domain.context.UserContext;

public interface UserAnswersService {

    UserAnswerHistory saveAnswer(UserContext context);
}
