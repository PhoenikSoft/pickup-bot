package com.phoeniksoft.pickupbot.infrastructure.telegram.notification

import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.core.user.UserStore
import com.phoeniksoft.pickupbot.domain.notification.GlobalMessage
import com.phoeniksoft.pickupbot.infrastructure.telegram.PickupBot
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import spock.lang.Specification

import static org.mockito.Mockito.when

class TelegramNotificationServiceTest extends Specification {

    @Mock
    UserStore userStore
    @Mock
    PickupBot pickupBot
    @InjectMocks
    TelegramNotificationService telegramNotificationService

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test notify All"() {
        given:
        when(userStore.getAll()).thenReturn([new User("testId")])
        def captor = ArgumentCaptor.forClass(SendMessage)
        when(pickupBot.execute((SendMessage) captor.capture())).thenReturn(null)

        when:
        telegramNotificationService.notifyAll(new GlobalMessage("testMsg"))

        then:
        captor.value.chatId == 'testId'
        captor.value.text == 'testMsg'
    }
}