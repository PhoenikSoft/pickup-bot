package com.phoeniksoft.pickupbot.infrastructure.jpa.history

import com.phoeniksoft.pickupbot.common.TestData
import com.phoeniksoft.pickupbot.domain.advisor.Advice
import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.history.UserHistory
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserRepository
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.mockito.Mockito.when

class JpaHistoryServiceTest extends Specification implements TestData {
    @Mock
    UserHistoryRepository userHistoryRepository
    @Mock
    UserRepository userRepository
    @InjectMocks
    JpaHistoryService jpaHistoryService

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test save History"() {
        given:
        def telegramId = 'testTelegram'
        def adviceId = 1
        def userDto = validUserDto()
        when(userRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(userDto))
        def userHistory = new UserHistoryDto()
        userHistory.user = userDto
        userHistory.adviceId = adviceId
        when(userHistoryRepository.save(userHistory)).thenReturn(userHistory)

        when:
        UserHistory result = jpaHistoryService.saveHistory(new User(telegramId), Advice.builder().id(adviceId.toString()).build())

        then:
        result.adviceId == adviceId.toString()
        result.user.id == telegramId
    }

    def "test save History if not found user"() {
        given:
        def telegramId = 'testTelegram'
        when(userRepository.findByTelegramId(telegramId)).thenReturn(Optional.empty())

        when:
        jpaHistoryService.saveHistory(new User(telegramId), Advice.builder().build())

        then:
        def ex = thrown IllegalArgumentException
        ex.message == 'There is no user with such telegram id for save'
    }

    def "test get Last Advice Id"() {
        given:
        def telegramId = 'id'
        def userDto = validUserDto()
        when(userRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(userDto))
        when(userHistoryRepository.findFirstByUserOrderByCreatedDesc(userDto)).thenReturn(Optional.of(validUserHistoryDto()))

        when:
        def result = jpaHistoryService.getLastAdviceId(new User(telegramId)).get()

        then:
        result == '1'
    }

    def "test get Last Advice Id if not found"() {
        given:
        def telegramId = 'id'
        def userDto = validUserDto()
        when(userRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(userDto))
        when(userHistoryRepository.findFirstByUserOrderByCreatedDesc(userDto)).thenReturn(Optional.empty())

        when:
        def result = jpaHistoryService.getLastAdviceId(new User(telegramId))

        then:
        !result.isPresent()
    }

    def "test exception thrown in get last advice if not found user"() {
        given:
        def telegramId = 'id'
        when(userRepository.findByTelegramId(telegramId)).thenReturn(Optional.empty())

        when:
        jpaHistoryService.getLastAdviceId(new User(telegramId))

        then:
        def ex = thrown IllegalArgumentException
        ex.message == 'There is no user with such telegram id for save'
    }

    def "test get Past Advices Ids"() {
        given:
        def telegramId = 'id'
        def userDto = validUserDto()
        when(userRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(userDto))
        when(userHistoryRepository.findDistinctAdviceIdsByUserId(userDto)).thenReturn([1l, 3L, 5L] as Set<Long>)

        when:
        Set<String> result = jpaHistoryService.getPastAdvicesIds(new User("id"))

        then:
        result == ['1', '3', '5'] as Set<String>
    }

    def "test get Past Advices Ids if user not found"() {
        given:
        def telegramId = 'id'
        when(userRepository.findByTelegramId(telegramId)).thenReturn(Optional.empty())

        when:
        jpaHistoryService.getPastAdvicesIds(new User("id"))

        then:
        def ex = thrown IllegalArgumentException
        ex.message == 'There is no user with such telegram id for save'
    }
}