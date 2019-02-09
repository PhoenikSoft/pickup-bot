package com.phoeniksoft.pickupbot.domain.context.interceptors

import com.phoeniksoft.pickupbot.domain.context.AdviceGoal
import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.UserCommand
import com.phoeniksoft.pickupbot.domain.core.UserQuery
import spock.lang.Specification

class UserCommandInterceptorSpec extends Specification {

    UserCommandInterceptor userCommandInterceptor = new UserCommandInterceptor()

    def "test fill context command"() {
        given:
        def query = UserQuery.builder().command(command).build()
        def context = new UserContext()

        when:
        userCommandInterceptor.fillContext(context, query)

        then:
        context.getUserIntent() == expected

        where:
        command                        | expected
        null                           | AdviceGoal.START_MESSAGE
        UserCommand.GET_START_ADVICE   | AdviceGoal.START_MESSAGE
        UserCommand.GET_NEXT_ADVICE    | AdviceGoal.NEXT_ADVICE
        UserCommand.GET_DATE_ADVICE    | AdviceGoal.DATE_ADVICE
        UserCommand.GET_PROFILE_ADVICE | AdviceGoal.PROFILE_IMPROVEMENT
        UserCommand.RATE_ADVICE        | AdviceGoal.START_MESSAGE
    }
}