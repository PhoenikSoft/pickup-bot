package com.phoeniksoft.pickupbot.infrastructure.telegram.utils


import spock.lang.Specification

class UsersTransactionsStorageSpec extends Specification {

    def usersTransactionsStorage = new UsersTransactionsStorage("maximumSize=3,expireAfterWrite=2s")

    def setup() {
        usersTransactionsStorage.init()
    }

    def "test put"() {
        when:
        usersTransactionsStorage.put(1l, Boolean.TRUE)

        then:
        usersTransactionsStorage.usersWithRequestedInput.asMap().get(1L)
    }

    def "test get - valid"() {
        given:
        usersTransactionsStorage.usersWithRequestedInput.asMap()[1L] = true

        when:
        Boolean result = usersTransactionsStorage.get(1l)

        then:
        result
    }

    def "test get - value expired"() {
        given:
        usersTransactionsStorage.usersWithRequestedInput.asMap()[1L] = true

        when:
        sleep(2500)
        Boolean result = usersTransactionsStorage.get(1l)

        then:
        !result
    }

    def "test get - size testing"() {
        given:
        usersTransactionsStorage.usersWithRequestedInput.asMap()[1L] = true
        usersTransactionsStorage.usersWithRequestedInput.asMap()[2L] = true
        usersTransactionsStorage.usersWithRequestedInput.asMap()[3L] = true

        when:
        usersTransactionsStorage.put(4l, true)

        then:
        usersTransactionsStorage.usersWithRequestedInput.asMap()[4L]
        usersTransactionsStorage.usersWithRequestedInput.asMap().size() == 3
    }

    def "test remove"() {
        given:
        usersTransactionsStorage.usersWithRequestedInput.asMap()[1L] = true

        when:
        boolean result = usersTransactionsStorage.remove(1l)

        then:
        result
        usersTransactionsStorage.usersWithRequestedInput.asMap()[1L] == null
    }
}