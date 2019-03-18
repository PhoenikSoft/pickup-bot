package com.phoeniksoft.pickupbot.domain.history;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.core.user.User;

import java.util.Optional;
import java.util.Set;

/**
 * Service that manages history records and events done by Pickup Core.
 */
public interface HistoryService {

    UserHistory saveHistory(User user, Advice advice);

    /**
     * Returns advice that was given the last for user.
     */
    Optional<String> getLastAdviceId(User user);

    /**
     * Returns all distinct advices that user have ever retrieved from Core.
     */
    Set<String> getPastAdvicesIds(User user);
}
