package com.phoeniksoft.pickupbot.domain.advisor;

import com.phoeniksoft.pickupbot.domain.core.user.User;

import java.util.Optional;

public interface AdviceStore {

    Optional<Advice> getById(String id);

    Advice getAdviceByTypeForUser(AdviceType type, User user);

    Optional<Advice> getNextAdvice(String prevAdviceId, NextAdviceParams params);

    Advice getDefaultAdvice();
}
