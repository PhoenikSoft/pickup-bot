package com.phoeniksoft.pickupbot.infrastructure.web.dto;

import com.phoeniksoft.pickupbot.domain.notification.GlobalMessage;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GlobalMessageDto {

    @NotEmpty
    private String msg;

    public GlobalMessage toGlobalMessage() {
        return new GlobalMessage(msg);
    }
}
