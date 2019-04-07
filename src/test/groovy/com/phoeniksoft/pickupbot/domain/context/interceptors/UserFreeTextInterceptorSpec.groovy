package com.phoeniksoft.pickupbot.domain.context.interceptors

import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.UserCommand
import com.phoeniksoft.pickupbot.domain.core.UserMessage
import com.phoeniksoft.pickupbot.domain.core.UserQuery
import spock.lang.Specification

class UserFreeTextInterceptorSpec extends Specification {

    def userFreeTextInterceptor = new UserFreeTextInterceptor()

    def "test fill context"() {
        given:
        def context = new UserContext()
        def query = UserQuery.builder()
                .command(UserCommand.ADD_USER_PROPOSAL)
                .message(new UserMessage('free text'))
                .build()

        when:
        userFreeTextInterceptor.fillContext(context, query)

        then:
        context.getUserFreeText() == 'free text'
    }

    def "test acceptable method of interceptor"() {
        given:
        def context = new UserContext()
        def userMessage = message ? new UserMessage(message) : null
        def query = UserQuery.builder().command(command).message(userMessage).build()

        when:
        def acceptable = userFreeTextInterceptor.isAcceptable(context, query)

        then:
        acceptable == expected

        where:
        command                       | message | expected
        null                          | "msg"   | false
        UserCommand.ADD_USER_PROPOSAL | "msg"   | true
        UserCommand.ADD_USER_PROPOSAL | null    | false
    }
}