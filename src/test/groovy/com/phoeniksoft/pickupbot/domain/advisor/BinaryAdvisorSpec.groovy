package com.phoeniksoft.pickupbot.domain.advisor

import com.phoeniksoft.pickupbot.domain.advisor.exception.NoNewAdviceForUserException
import com.phoeniksoft.pickupbot.domain.advisor.exception.NoPrevAdviceFoundException
import com.phoeniksoft.pickupbot.domain.context.AdviceGoal
import com.phoeniksoft.pickupbot.domain.context.UserAnswer
import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.utils.AdvisorTestData
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.eq
import static org.mockito.Mockito.when

class BinaryAdvisorSpec extends Specification implements AdvisorTestData {
    @Mock
    AdviceStore adviceStore
    @Mock
    MessageStore messageStore
    @InjectMocks
    BinaryAdvisor binaryAdvisor

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test get start advice with not empty advice"() {
        given:
        def expectedAdvice = validAdvice()
        when(messageStore.getStartMessageForUser(new User("testId"))).thenReturn(expectedAdvice)

        when:
        Advice result = binaryAdvisor.getAdvice(new UserContext(
                userIntent: AdviceGoal.START_MESSAGE,
                user: new User("testId")))

        then:
        result == expectedAdvice
    }

    def "test get start advice with empty advice"() {
        given:
        when(messageStore.getStartMessageForUser(new User("testId"))).thenThrow(NoNewAdviceForUserException)

        when:
        binaryAdvisor.getAdvice(new UserContext(
                userIntent: AdviceGoal.START_MESSAGE,
                user: new User("testId")))

        then:
        thrown NoNewAdviceForUserException
    }

    def "test get default advice if unknown command"() {
        given:
        def expectedAdvice = validAdvice()
        when(adviceStore.getDefaultAdvice()).thenReturn(expectedAdvice)

        when:
        Advice result = binaryAdvisor.getAdvice(new UserContext(userIntent: AdviceGoal.DATE_INVITATION))

        then:
        result == expectedAdvice
    }

    def "test get next advice"() {
        given:
        def expectedAdvice = validAdvice()
        when(adviceStore.getNextAdvice(eq("test"), eq(new NextAdviceParams([userAnswer: UserAnswer.YES]))))
                .thenReturn(Optional.of(expectedAdvice))

        when:
        Advice result = binaryAdvisor.getAdvice(
                new UserContext(userIntent: AdviceGoal.NEXT_ADVICE,
                        userAnswer: UserAnswer.YES,
                        payload: ["prevAdviceId": "test"]))

        then:
        result == expectedAdvice
    }

    def "test get next advice without user answer"() {
        when:
        binaryAdvisor.getAdvice(new UserContext(userIntent: AdviceGoal.NEXT_ADVICE))

        then:
        thrown NullPointerException
    }

    def "test get next advice without payload"() {
        when:
        binaryAdvisor.getAdvice(
                new UserContext(userIntent: AdviceGoal.NEXT_ADVICE,
                        user: new User("testId"),
                        userAnswer: UserAnswer.YES))

        then:
        def ex = thrown NoPrevAdviceFoundException
        ex.message == 'Cannot find previous advice'
    }

    def "test get next advice when advice store cannot find next advice"() {
        given:
        when(adviceStore.getNextAdvice(eq("test"), eq(new NextAdviceParams([userAnswer: UserAnswer.YES]))))
                .thenReturn(Optional.empty())
        def expectedAdvice = validAdvice()
        when(adviceStore.getDefaultAdvice()).thenReturn(expectedAdvice)

        when:
        Advice result = binaryAdvisor.getAdvice(
                new UserContext(userIntent: AdviceGoal.NEXT_ADVICE,
                        userAnswer: UserAnswer.YES,
                        payload: ["prevAdviceId": "test"]))

        then:
        result == expectedAdvice
    }

    def "test get date advice"() {
        given:
        def expectedAdvice = validAdvice()
        when(adviceStore.getAdviceByTypeForUser(AdviceType.DATE, new User("testId"))).thenReturn(expectedAdvice)

        when:
        Advice result = binaryAdvisor.getAdvice(new UserContext(
                userIntent: AdviceGoal.DATE_ADVICE,
                user: new User("testId")))

        then:
        result == expectedAdvice
    }

    def "test get profile advice"() {
        given:
        def expectedAdvice = validAdvice()
        when(adviceStore.getAdviceByTypeForUser(AdviceType.PROFILE, new User("testId"))).thenReturn(expectedAdvice)

        when:
        Advice result = binaryAdvisor.getAdvice(new UserContext(
                userIntent: AdviceGoal.PROFILE_IMPROVEMENT,
                user: new User("testId")))

        then:
        result == expectedAdvice
    }
}