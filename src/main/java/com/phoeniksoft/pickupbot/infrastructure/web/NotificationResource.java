package com.phoeniksoft.pickupbot.infrastructure.web;

import com.phoeniksoft.pickupbot.domain.notification.NotificationService;
import com.phoeniksoft.pickupbot.infrastructure.web.dto.GlobalMessageDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class NotificationResource extends NotificationResourceV1 {

    private final NotificationService notificationService;

    @PutMapping
    public String notifyAllUsers(@RequestBody @Valid GlobalMessageDto globalMessageDto) {
        notificationService.notifyAll(globalMessageDto.toGlobalMessage());
        return ALL_USERS_NOTIFIED_MSG;
    }
}
