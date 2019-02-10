package com.phoeniksoft.pickupbot.infrastructure.jpa.answer

import com.phoeniksoft.pickupbot.common.TestData
import com.phoeniksoft.pickupbot.domain.context.UserAnswer
import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.history.UserAnswerHistory
import com.phoeniksoft.pickupbot.infrastructure.jpa.history.UserHistoryRepository
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserRepository
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.mockito.Mockito.when

class JpaUserAnswersServiceSpec extends Specification implements TestData {
    @Mock
    UserAnswersRepository userAnswersRepository
    @Mock
    UserRepository userRepository
    @Mock
    UserHistoryRepository userHistoryRepository
    @InjectMocks
    JpaUserAnswersService jpaUserAnswersService

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test save valid user answer"() {
        given:
        def userDto = validUserDto()
        when(userRepository.findByTelegramId("testUser")).thenReturn(Optional.of(userDto))
        when(userHistoryRepository.findFirstByUserOrderByCreatedDesc(userDto)).thenReturn(Optional.of(validUserHistoryDto()))
        def userContext = new UserContext()
        userContext.user = new User("testUser")
        userContext.userAnswer = UserAnswer.LIKE_ADVICE

        def answerDto = new UserAnswerDto()
        answerDto.adviceId = 1
        answerDto.answer = UserAnswer.LIKE_ADVICE.toString()
        answerDto.user = userDto
        when(userAnswersRepository.save(answerDto)).thenReturn(answerDto)

        when:
        UserAnswerHistory result = jpaUserAnswersService.saveAnswer(userContext)

        then:
        result.answer == 'LIKE_ADVICE'
        result.user.id == 'testTelegram'
        result.adviceId == '1'
    }

    def "test throw exception when not found user"() {
        given:
        when(userRepository.findByTelegramId("testUser")).thenReturn(Optional.empty())
        def userContext = new UserContext()
        userContext.user = new User("testUser")

        when:
        jpaUserAnswersService.saveAnswer(userContext)

        then:
        def ex = thrown IllegalArgumentException
        ex.message == 'There is no user with such telegram id for save'
    }

    def "test throw exception when there is no history for user"() {
        given:
        def userDto = validUserDto()
        when(userRepository.findByTelegramId("testUser")).thenReturn(Optional.of(userDto))
        when(userHistoryRepository.findFirstByUserOrderByCreatedDesc(userDto)).thenReturn(Optional.empty())
        def userContext = new UserContext()
        userContext.user = new User("testUser")

        when:
        jpaUserAnswersService.saveAnswer(userContext)

        then:
        def ex = thrown IllegalStateException
        ex.message == 'Cannot save user answer because there is no advices in user history'
    }
}