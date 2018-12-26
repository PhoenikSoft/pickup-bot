package com.phoeniksoft.pickupbot.domain.context.interceptors

import com.phoeniksoft.pickupbot.domain.context.AdviceType
import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.UserQuery
import spock.lang.*
import com.phoeniksoft.pickupbot.domain.core.UserCommand

class UserCommandInterceptorSpec extends Specification {

    UserCommandInterceptor userCommandInterceptor = new UserCommandInterceptor()

    def "test null command"() {
        given:
        def query = UserQuery.builder().build()
        def context = new UserContext()

        when:
        userCommandInterceptor.fillContext(context, query)

        then:
        thrown NullPointerException
    }

    def "test fill context command"() {
        given:
        def query = UserQuery.builder().command(command).build()
        def context = new UserContext()

        when:
        userCommandInterceptor.fillContext(context, query)

        then:
        context.getUserIntent() == expected

        where:
        command                      | expected
        UserCommand.GET_START_ADVICE | AdviceType.START_MESSAGE
        UserCommand.GET_NEXT_ADVICE  | AdviceType.NEXT_STEP
        UserCommand.FINISH_ADVICE    | AdviceType.DATE_INVITATION
    }
}