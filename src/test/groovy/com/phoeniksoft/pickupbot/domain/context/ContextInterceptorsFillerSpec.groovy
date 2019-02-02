package com.phoeniksoft.pickupbot.domain.context

import com.phoeniksoft.pickupbot.domain.context.interceptors.ContextInterceptor
import com.phoeniksoft.pickupbot.domain.core.UserQuery
import spock.lang.Specification

class ContextInterceptorsFillerSpec extends Specification {

    def interceptor1 = Mock(ContextInterceptor)
    def interceptor2 = Mock(ContextInterceptor)
    def interceptors = [interceptor1, interceptor2]
    def contextInterceptorsFiller = new ContextInterceptorsFiller(interceptors)

    def "test fill Context"() {
        given:
        def userQuery = UserQuery.builder().build()
        interceptor2.isAcceptable(_, _) >> true

        when:
        UserContext result = contextInterceptorsFiller.fillContext(userQuery)

        then:
        1 * interceptor2.fillContext(_, userQuery)
        0 * interceptor1.fillContext(_, _)
        result != null
    }
}