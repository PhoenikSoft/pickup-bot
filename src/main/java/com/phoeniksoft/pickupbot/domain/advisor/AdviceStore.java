package com.phoeniksoft.pickupbot.domain.advisor;

import java.util.Optional;

public interface AdviceStore {

    Optional<Advice> getById(String id);

    Optional<Advice> getNextAdvice(String prevAdviceId, NextAdviceParams params);

    Optional<Advice> getStartAdvice();

    Advice getDefaultAdvice();
}
