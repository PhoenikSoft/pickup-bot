package com.phoeniksoft.pickupbot.domain.history;

import com.phoeniksoft.pickupbot.domain.context.UserContext;

/**
 * Service that manages user answers.
 */
public interface UserAnswersService {

    UserAnswerHistory saveAnswer(UserContext context);
}
