package com.phoeniksoft.pickupbot.infrastructure.web;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/notifications")
public abstract class NotificationResourceV1 {

    static final String ALL_USERS_NOTIFIED_MSG = "All users have notified!";
}
