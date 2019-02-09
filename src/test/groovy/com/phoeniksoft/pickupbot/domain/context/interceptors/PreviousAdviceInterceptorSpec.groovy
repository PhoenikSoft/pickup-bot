package com.phoeniksoft.pickupbot.domain.context.interceptors

import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.UserCommand
import com.phoeniksoft.pickupbot.domain.core.UserQuery
import com.phoeniksoft.pickupbot.domain.core.UserQueryParams
import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.history.HistoryService
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.verifyNoMoreInteractions
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
        def query = UserQuery.builder().command(UserCommand.GET_NEXT_ADVICE).build()

        when:
        def acceptable = previousAdviceInterceptor.isAcceptable(context, query)

        then:
        !acceptable
    }

    def "should get previous advice id from user query params"() {
        given:
        def context = new UserContext()
        def userQuery = UserQuery.builder()
                .specificParams(new UserQueryParams([(UserQueryParams.ADVICE_ID_PARAM): "testAdviceId"]))
                .build()

        when:
        previousAdviceInterceptor.fillContext(context, userQuery)

        then:
        context.getPayload() == [(UserContext.ContextPayload.PREV_ADVICE_PARAM): "testAdviceId"]
        verifyNoMoreInteractions(historyService)
    }

    def "test if there is no history for user"() {
        given:
        when(historyService.getLastAdviceId(any())).thenReturn Optional.empty()
        def context = new UserContext()
        context.setUser(new User("test"))

        when:
        previousAdviceInterceptor.fillContext(context, UserQuery.builder().build())

        then:
        context.getPayload().isEmpty()
    }

    def "test fill context with last advice id"() {
        given:
        def context = new UserContext()
        def user = new User("test")
        context.setUser(user)
        when(historyService.getLastAdviceId(user)).thenReturn(Optional.of("last"))
        def query = UserQuery.builder().build()

        when:
        previousAdviceInterceptor.fillContext(context, query)

        then:
        context.getPayload().get(UserContext.ContextPayload.PREV_ADVICE_PARAM) == "last"
    }
}