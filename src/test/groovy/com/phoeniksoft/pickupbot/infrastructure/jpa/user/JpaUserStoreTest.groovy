package com.phoeniksoft.pickupbot.infrastructure.jpa.user

import com.phoeniksoft.pickupbot.common.TestData
import com.phoeniksoft.pickupbot.domain.core.user.User
import spock.lang.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import static org.mockito.Mockito.*

class JpaUserStoreTest extends Specification implements TestData {
    @Mock
    UserRepository userRepository
    @InjectMocks
    JpaUserStore jpaUserStore

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test find By Id"() {
        given:
        def telegramId = 'id'
        when(userRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(validUserDto()))

        when:
        User result = jpaUserStore.findById(telegramId).get()

        then:
        result.id == 'testTelegram'
    }

    def "test find By Id not found"() {
        given:
        def telegramId = 'id'
        when(userRepository.findByTelegramId(telegramId)).thenReturn(Optional.empty())

        when:
        Optional<User> result = jpaUserStore.findById(telegramId)

        then:
        !result.isPresent()
    }

    def "test save"() {
        given:
        def userDto = new UserDto()
        def telegramId = 'testTelegram'
        userDto.telegramId = telegramId
        when(userRepository.save(userDto)).thenReturn(userDto)

        when:
        User result = jpaUserStore.save(new User(telegramId))

        then:
        result.id == telegramId
    }
}