package com.phoeniksoft.pickupbot.domain.history;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.core.user.User;

import java.util.Optional;

public interface HistoryService {

    UserHistory saveHistory(User user, Advice advice);

    Optional<String> getLastAdviceId(User user);
}
