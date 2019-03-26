package com.phoeniksoft.pickupbot.infrastructure.web;

import com.phoeniksoft.pickupbot.domain.notification.NotificationService;
import com.phoeniksoft.pickupbot.domain.notification.Topic;
import com.phoeniksoft.pickupbot.infrastructure.web.dto.GlobalMessageDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST controller that gives entry points for notifying users.
 */
@RestController
@AllArgsConstructor
public class NotificationResource extends NotificationResourceV1 {

    private final NotificationService notificationService;

    @PutMapping
    public String notifyUsers(@RequestParam(required = false) Topic topic,
                              @RequestBody @Valid GlobalMessageDto globalMessageDto) {
        if (topic != null) {
            notificationService.notifyByTopic(globalMessageDto.toGlobalMessage(), topic);
        } else {
            notificationService.notifyAll(globalMessageDto.toGlobalMessage());
        }
        return USERS_NOTIFIED_MSG;
    }
}
