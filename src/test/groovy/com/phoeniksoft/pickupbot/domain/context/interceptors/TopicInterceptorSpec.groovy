package com.phoeniksoft.pickupbot.domain.context.interceptors

import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.UserCommand
import com.phoeniksoft.pickupbot.domain.core.UserQuery
import com.phoeniksoft.pickupbot.domain.core.UserQueryParams
import com.phoeniksoft.pickupbot.domain.notification.Topic
import spock.lang.Specification

class TopicInterceptorSpec extends Specification {

    def topicInterceptor = new TopicInterceptor()

    def "test interceptor is applicable for subscribe command"() {
        given:
        def context = new UserContext()
        def query = UserQuery.builder().command(UserCommand.SUBSCRIBE_TO_TOPIC).build()

        when:
        def acceptable = topicInterceptor.isAcceptable(context, query)

        then:
        acceptable
    }

    def "should fill context with topic param"() {
        given:
        def context = new UserContext()
        def userQuery = UserQuery.builder()
                .specificParams(new UserQueryParams([(UserQueryParams.TOPIC_PARAM): Topic.PROFILE]))
                .build()

        when:
        topicInterceptor.fillContext(context, userQuery)

        then:
        context.getPayload() == [(UserContext.ContextPayload.TOPIC_PARAM): Topic.PROFILE]
    }

    def "throws exception if query without topic param"() {
        given:
        def context = new UserContext()

        when:
        topicInterceptor.fillContext(context, UserQuery.builder().build())

        then:
        def ex = thrown(IllegalStateException)
        ex.message == 'Topic param is mandatory'
    }

    def "throws exception if topic param has incorrect type"() {
        given:
        def context = new UserContext()
        def query = UserQuery.builder()
                .specificParams(new UserQueryParams([(UserQueryParams.TOPIC_PARAM): 'PROFILE']))
                .build()

        when:
        topicInterceptor.fillContext(context, query)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Topic param isn\'t of the correct type'
    }
}