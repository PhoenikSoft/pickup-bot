package com.phoeniksoft.pickupbot.domain.context.interceptors

import com.phoeniksoft.pickupbot.domain.context.UserAnswer
import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.UserMessage
import com.phoeniksoft.pickupbot.domain.core.UserQuery
import spock.lang.*

class UserAnswerInterceptorSpec extends Specification {

    UserAnswerInterceptor userAnswerInterceptor = new UserAnswerInterceptor()

    def "test fill context"() {
        given:
        def context = new UserContext()
        def query = UserQuery.builder().message(new UserMessage("YES")).build()

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
        userAnswerInterceptor.fillContext(context, query)

        then:
        context.getUserAnswer() == null
    }

    def "test if user answer is unaccepted"() {
        given:
        def context = new UserContext()
        def query = UserQuery.builder().message(new UserMessage("invalid")).build()

        when:
        userAnswerInterceptor.fillContext(context, query)

        then:
        thrown IllegalArgumentException
    }
}