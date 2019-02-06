package com.phoeniksoft.pickupbot.domain.advisor;

public interface MessageStore {

    Advice getStartMessageForUser(String userId);
}
