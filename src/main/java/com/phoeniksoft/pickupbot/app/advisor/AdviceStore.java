package com.phoeniksoft.pickupbot.app.advisor;

import com.phoeniksoft.pickupbot.model.Advice;

public interface AdviceStore {

    Advice getById(String id);

    Advice getStartAdvice();

    Advice getDefaultAdvice();
}
