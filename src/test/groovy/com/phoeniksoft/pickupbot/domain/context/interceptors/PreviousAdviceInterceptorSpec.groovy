package com.phoeniksoft.pickupbot.domain.context.interceptors

import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.history.HistoryService
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class PreviousAdviceInterceptorSpec extends Specification {

    @Mock
    HistoryService historyService

    @InjectMocks
    PreviousAdviceInterceptor previousAdviceInterceptor

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test empty user in context"() {
        given:
        def context = new UserContext()

        when:
        previousAdviceInterceptor.fillContext(context, null)

        then:
        context.getPayload().isEmpty()
    }

    def "test if there is no history for user"() {
        given:
        historyService.getLastAdviceId(any()) >> Optional.empty()
        def context = new UserContext()
        context.setUser(new User("test"))

        when:
        previousAdviceInterceptor.fillContext(context, null)

        then:
        context.getPayload().isEmpty()
    }

    def "test fill context with last advice id"() {
        given:
        def context = new UserContext()
        def user = new User("test")
        context.setUser(user)
        when(historyService.getLastAdviceId(any())).thenReturn(Optional.of("last"))

        when:
        previousAdviceInterceptor.fillContext(context, null)

        then:
        context.getPayload().get(UserContext.ContextPayload.PREV_ADVICE_PARAM) == "last"
    }
}