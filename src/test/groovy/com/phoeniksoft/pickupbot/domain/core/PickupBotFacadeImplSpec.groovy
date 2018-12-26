package com.phoeniksoft.pickupbot.domain.core

import com.phoeniksoft.pickupbot.domain.advisor.Advisor
import com.phoeniksoft.pickupbot.domain.context.ContextFiller
import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.history.HistoryService
import com.phoeniksoft.pickupbot.utils.AdvisorTestData
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.eq
import static org.mockito.Mockito.when

class PickupBotFacadeImplSpec extends Specification implements AdvisorTestData {
    @Mock
    ContextFiller contextFiller
    @Mock
    Advisor advisor
    @Mock
    HistoryService historyService
    @InjectMocks
    PickupBotFacadeImpl pickupBotFacadeImpl

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test get advice - full road"() {
        given:
        def context = validUserContext()
        when(contextFiller.fillContext(any(UserQuery))).thenReturn(context)
        def expectedAdvice = validAdvice()
        when(advisor.getAdvice(context)).thenReturn(expectedAdvice)
        when(historyService.saveHistory(any(User), eq(expectedAdvice))).thenReturn(null)

        when:
        UserAdvice result = pickupBotFacadeImpl.getAdvice(new UserQuery(UserCommand.GET_START_ADVICE,
                new UserMessage("value"),
                new UserQueryParams()))

        then:
        result.msg == "testMsg"
        result.payload == [adviceId: "testId"]
    }
}