package com.phoeniksoft.pickupbot.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoeniksoft.pickupbot.domain.notification.GlobalMessage;
import com.phoeniksoft.pickupbot.domain.notification.NotificationService;
import com.phoeniksoft.pickupbot.infrastructure.web.dto.GlobalMessageDto;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.phoeniksoft.pickupbot.infrastructure.web.NotificationResourceV1.ALL_USERS_NOTIFIED_MSG;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NotificationResource.class)
public class NotificationResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationService notificationService;

    @Test
    @SneakyThrows
    public void testNotifyAllUsers() {
        String body = objectMapper.writeValueAsString(mockGlobalMessage());

        mvc.perform(put("/api/v1/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(ALL_USERS_NOTIFIED_MSG));
        verify(notificationService).notifyAll(new GlobalMessage("testMsg"));
    }

    @Test
    @SneakyThrows
    public void testNotifyAllUsers_emptyMsg() {
        GlobalMessageDto globalMessageDto = new GlobalMessageDto();
        globalMessageDto.setMsg("");
        String body = objectMapper.writeValueAsString(globalMessageDto);

        mvc.perform(put("/api/v1/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }

    private static GlobalMessageDto mockGlobalMessage() {
        GlobalMessageDto mockObj = new GlobalMessageDto();
        mockObj.setMsg("testMsg");
        return mockObj;
    }
}
