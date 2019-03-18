package com.phoeniksoft.pickupbot.domain.notification;

/**
 * Service that notifies users with some messages.
 */
public interface NotificationService {

    void notifyAll(GlobalMessage globalMessage);
}
