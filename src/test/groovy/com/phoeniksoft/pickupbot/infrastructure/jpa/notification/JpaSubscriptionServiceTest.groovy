package com.phoeniksoft.pickupbot.infrastructure.jpa.notification

import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.notification.Subscription
import com.phoeniksoft.pickupbot.domain.notification.Topic
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserRepository
import spock.lang.Specification

import java.time.LocalDateTime

class JpaSubscriptionServiceTest extends Specification {
    UserSubscriptionRepository userSubscriptionRepository = Mock()
    UserRepository userRepository = Mock()
    def jpaSubscriptionService = new JpaSubscriptionService(userSubscriptionRepository, userRepository)

    def "test subscribe - valid"() {
        given:
        1 * userRepository.findByTelegramId('testId') >> Optional.of(new UserDto(id: 1L, telegramId: 'testId'))
        1 * userSubscriptionRepository.save({
            it.user.telegramId == 'testId' && it.user.id == 1L && it.topic == Topic.MESSAGE
        }) >> { args ->
            new UserSubscriptionDto(id: 1L, user: args[0].user, topic: args[0].topic, created: LocalDateTime.now())
        }

        when:
        Subscription result = jpaSubscriptionService.subscribe(new User("testId"), Topic.MESSAGE)

        then:
        result.user.id == 'testId'
        result.topic == Topic.MESSAGE
    }

    def "test subscribe - no user found"() {
        given:
        1 * userRepository.findByTelegramId('testId') >> Optional.empty()

        when:
        jpaSubscriptionService.subscribe(new User("testId"), Topic.MESSAGE)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'There is no user with such telegram id for save'
        0 * userSubscriptionRepository.save(_)
    }

    def "test subscribe - duplicate found"() {
        given:
        1 * userRepository.findByTelegramId('testId') >> Optional.of(new UserDto(id: 1L, telegramId: 'testId'))
        1 * userSubscriptionRepository.existsByUserAndTopic({ it.id == 1L }, Topic.MESSAGE) >> true

        when:
        jpaSubscriptionService.subscribe(new User("testId"), Topic.MESSAGE)

        then:
        def ex = thrown(UserWithTopicDuplicateException)
        ex.message == 'Cannot save user subscription, because there is already user [1] with topic [MESSAGE] in DB'
        0 * userSubscriptionRepository.save(_)
    }

    def "test getting subscribed users to topic"() {
        given:
        1 * userSubscriptionRepository.getUsersSubscribedToTopic(Topic.MESSAGE) >>
                Arrays.asList(new UserDto(id: 1L, telegramId: 'testId'))

        when:
        def users = jpaSubscriptionService.getUsersSubscribedToTopic(Topic.MESSAGE)

        then:
        users == [new User('testId')]
    }
}