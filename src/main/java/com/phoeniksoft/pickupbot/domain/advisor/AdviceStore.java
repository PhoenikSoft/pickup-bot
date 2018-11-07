package com.phoeniksoft.pickupbot.domain.advisor;

public interface AdviceStore<T> {

    Advice getById(T id);

    Advice getStartAdvice();

    Advice getDefaultAdvice();
}
