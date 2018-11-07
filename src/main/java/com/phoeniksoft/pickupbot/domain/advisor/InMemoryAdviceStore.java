package com.phoeniksoft.pickupbot.domain.advisor;

import javax.annotation.PostConstruct;
import java.util.*;

public class InMemoryAdviceStore implements AdviceStore<String> {

    private static final Advice DEFAULT_ADVICE = new Advice("-1", "Try to invite her for a date.", Optional.empty());

    private Map<String, Advice> advicesById;

    private List<Advice> startAdvices;

    private final Random ran = new Random();

    @PostConstruct
    private void init() {
        advicesById = new HashMap<>(4);
        startAdvices = new ArrayList<>(2);

        Advice nextAdvice = new Advice("1", "Advice #12", Optional.empty());
        advicesById.put(nextAdvice.getId(), nextAdvice);
        Advice prevAdvice = new Advice("0", "Advice #11", Optional.of(nextAdvice));
        advicesById.put(prevAdvice.getId(), prevAdvice);
        startAdvices.add(prevAdvice);

        nextAdvice = new Advice("2", "Advice #22", Optional.empty());
        advicesById.put(nextAdvice.getId(), nextAdvice);
        prevAdvice = new Advice("3", "Advice #21", Optional.of(nextAdvice));
        advicesById.put(prevAdvice.getId(), prevAdvice);
        startAdvices.add(prevAdvice);
    }

    @Override
    public Advice getById(String id) {
        return advicesById.get(id);
    }

    @Override
    public Advice getStartAdvice() {
        return startAdvices.get(ran.nextInt(startAdvices.size()));
    }

    @Override
    public Advice getDefaultAdvice() {
        return DEFAULT_ADVICE;
    }
}
