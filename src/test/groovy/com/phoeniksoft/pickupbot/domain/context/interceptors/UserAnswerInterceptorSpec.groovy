package com.phoeniksoft.pickupbot.domain.context.interceptors

import com.phoeniksoft.pickupbot.domain.context.UserAnswer
import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.context.interceptors.exception.CannotRecognizeUserAnswerException
import com.phoeniksoft.pickupbot.domain.core.UserCommand
import com.phoeniksoft.pickupbot.domain.core.UserMessage
import com.phoeniksoft.pickupbot.domain.core.UserQuery
import spock.lang.Specification

class UserAnswerInterceptorSpec extends Specification {

    UserAnswerInterceptor userAnswerInterceptor = new UserAnswerInterceptor()

    def "test fill context"() {
        given:
        def context = new UserContext()
        def query = UserQuery.builder()
                .command(UserCommand.GET_NEXT_ADVICE)
                .message(new UserMessage("YES"))
                .build()

        when:
        def acceptable = userAnswerInterceptor.isAcceptable(context, query)

        then:
        acceptable

        when:
        userAnswerInterceptor.fillContext(context, query)

        then:
        context.getUserAnswer() == UserAnswer.YES
    }

    def "test if user answer is empty"() {
        given:
        def context = new UserContext()
        def query = UserQuery.builder().message().build()

        when:
        def acceptable = userAnswerInterceptor.isAcceptable(context, query)

        then:
        !acceptable

        when:
        userAnswerInterceptor.fillContext(context, query)

        then:
        thrown NullPointerException
    }

    def "test if user answer is unaccepted"() {
        given:
        def context = new UserContext()
        def query = UserQuery.builder().message(new UserMessage("invalid")).build()

        when:
        def acceptable = userAnswerInterceptor.isAcceptable(context, query)

        then:
        !acceptable

        when:
        userAnswerInterceptor.fillContext(context, query)

        then:
        thrown CannotRecognizeUserAnswerException
    }
}