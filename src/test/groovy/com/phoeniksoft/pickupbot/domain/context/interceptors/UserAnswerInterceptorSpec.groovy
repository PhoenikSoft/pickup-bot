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
        thrown NullPointerException
    }

    def "test if user answer is unaccepted"() {
        given:
        def context = new UserContext()
        def query = UserQuery.builder().message(new UserMessage("invalid")).build()

        when:
        userAnswerInterceptor.fillContext(context, query)

        then:
        thrown CannotRecognizeUserAnswerException
    }

    def "test acceptable method of interceptor"() {
        given:
        def context = new UserContext()
        def userMessage = message ? new UserMessage(message) : null
        def query = UserQuery.builder().command(command).message(userMessage).build()

        when:
        def acceptable = userAnswerInterceptor.isAcceptable(context, query)

        then:
        acceptable == expected

        where:
        command                        | message | expected
        null                           | "msg"   | false
        UserCommand.GET_START_ADVICE   | "msg"   | false
        UserCommand.GET_NEXT_ADVICE    | "msg"   | true
        UserCommand.GET_DATE_ADVICE    | "msg"   | false
        UserCommand.GET_PROFILE_ADVICE | "msg"   | false
        UserCommand.RATE_ADVICE        | "msg"   | true
        UserCommand.RATE_ADVICE        | null    | false
    }
}