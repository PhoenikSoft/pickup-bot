package com.phoeniksoft.pickupbot.domain.advisor;

import com.phoeniksoft.pickupbot.domain.core.user.User;

import java.util.Optional;

/**
 * Service that manages advice storing.
 */
public interface AdviceStore {

    /**
     * Returns optional advice by its id. With value - found, empty - not found.
     */
    Optional<Advice> getById(String id);

    /**
     * Returns next advice for user by type.
     *
     * @param type - the type of the advice to search
     */
    Advice getAdviceByTypeForUser(AdviceType type, User user);

    /**
     * Returns next advice for previous advice with params.
     */
    Optional<Advice> getNextAdvice(String prevAdviceId, NextAdviceParams params);

    /**
     * Returns some default advice if cannot determine the next one.
     */
    Advice getDefaultAdvice();

    /**
     * Returns the count of the all nodes in the store.
     */
    Long getNodesCount();
}
