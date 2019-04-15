package com.phoeniksoft.pickupbot.infrastructure.jpa.answer

import com.phoeniksoft.pickupbot.app.config.CoreProperties
import com.phoeniksoft.pickupbot.common.TestData
import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.history.TooManyUserProposalsException
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserRepository
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.mockito.Mockito.when

class JpaUserProposalsServiceSpec extends Specification implements TestData {
    @Mock
    UserProposalsRepository userProposalsRepository
    @Mock
    UserRepository userRepository
    @Mock
    CoreProperties coreProperties
    @InjectMocks
    JpaUserProposalsService jpaUserProposalsService

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test save valid user proposal"() {
        given:
        def userDto = validUserDto()
        when(userRepository.findByTelegramId("testUser")).thenReturn(Optional.of(userDto))
        when(coreProperties.getProposalsLimit()).thenReturn(1)
        def userContext = new UserContext()
        userContext.user = new User("testUser")
        userContext.userFreeText = "test text"

        def proposalDto = new UserProposalDto()
        proposalDto.message = "test text"
        proposalDto.user = userDto
        when(userProposalsRepository.save(proposalDto)).thenReturn(proposalDto)

        when:
        def result = jpaUserProposalsService.saveProposal(userContext)

        then:
        result.message == 'test text'
        result.user.id == 'testTelegram'
    }

    def "test throw exception when not found user"() {
        given:
        when(userRepository.findByTelegramId("testUser")).thenReturn(Optional.empty())
        def userContext = new UserContext()
        userContext.user = new User("testUser")

        when:
        jpaUserProposalsService.saveProposal(userContext)

        then:
        def ex = thrown IllegalArgumentException
        ex.message == 'There is no user with such telegram id for save'
    }

    def "test throw exception when proposals limit exceeded"() {
        given:
        def userDto = validUserDto()
        when(userRepository.findByTelegramId("testUser")).thenReturn(Optional.of(userDto))
        def userContext = new UserContext()
        userContext.user = new User("testUser")

        when:
        jpaUserProposalsService.saveProposal(userContext)

        then:
        def ex = thrown TooManyUserProposalsException
        ex.message == 'Too many user proposals. The limit is 0'
        ex.limit == 0
    }
}