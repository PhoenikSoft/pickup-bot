package com.phoeniksoft.pickupbot.domain.history;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.core.user.User;

import java.util.Optional;
import java.util.Set;

public interface HistoryService {

    UserHistory saveHistory(User user, Advice advice);

    Optional<String> getLastAdviceId(User user);

    Set<String> getPastAdvicesIds(User user);
}
