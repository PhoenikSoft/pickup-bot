package com.phoeniksoft.pickupbot.domain

import spock.lang.Specification

class HealthSpec extends Specification {

    def "health check: 1 == 1"() {
        when:
        def value = 1
        def expected = 1

        then:
        value == expected
    }
}
