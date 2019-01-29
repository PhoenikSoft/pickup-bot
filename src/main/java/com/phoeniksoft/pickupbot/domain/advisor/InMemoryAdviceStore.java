package com.phoeniksoft.pickupbot.domain.advisor;

import javax.annotation.PostConstruct;
import java.util.*;

import static com.phoeniksoft.pickupbot.domain.advisor.Advice.NEXT_ADVICE_PARAM;

public class InMemoryAdviceStore implements AdviceStore {

    private static final Advice DEFAULT_ADVICE = new Advice("-1", "Try to invite her for a date.");

    private Map<String, Advice> advicesById;

    private List<Advice> startAdvices;

    private final Random ran = new Random();

    @PostConstruct
    private void init() {
        advicesById = new HashMap<>(4);
        startAdvices = new ArrayList<>(2);

        Advice nextAdvice = new Advice("1", "Advice #12");
        advicesById.put(nextAdvice.getId(), nextAdvice);
        Advice prevAdvice = new Advice("0", "Advice #11");
        prevAdvice.getPayload().put(NEXT_ADVICE_PARAM, Optional.of(nextAdvice));
        advicesById.put(prevAdvice.getId(), prevAdvice);
        startAdvices.add(prevAdvice);

        nextAdvice = new Advice("2", "Advice #22");
        advicesById.put(nextAdvice.getId(), nextAdvice);
        prevAdvice = new Advice("3", "Advice #21");
        prevAdvice.getPayload().put(NEXT_ADVICE_PARAM, Optional.of(nextAdvice));
        advicesById.put(prevAdvice.getId(), prevAdvice);
        startAdvices.add(prevAdvice);
    }

    @Override
    public Optional<Advice> getById(String id) {
        return Optional.ofNullable(advicesById.get(id));
    }

    @Override
    public Optional<Advice> getNextAdvice(String prevAdviceId, NextAdviceParams params) {
        return getById(prevAdviceId)
                .flatMap(a -> Optional.of((Advice) a.getPayload().getOrDefault(NEXT_ADVICE_PARAM, DEFAULT_ADVICE)));
    }

    @Override
    public Optional<Advice> getStartAdvice() {
        return Optional.ofNullable(startAdvices.get(ran.nextInt(startAdvices.size())));
    }

    @Override
    public Advice getStartAdviceForUser(String userId) {
        return getStartAdvice().orElse(null);
    }

    @Override
    public Advice getDefaultAdvice() {
        return DEFAULT_ADVICE;
    }
}
