package com.phoeniksoft.pickupbot.infrastructure.neo4j

import com.phoeniksoft.pickupbot.common.TestData
import com.phoeniksoft.pickupbot.domain.advisor.Advice
import com.phoeniksoft.pickupbot.domain.advisor.exception.NoNewAdviceForUserException
import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.history.HistoryService
import spock.lang.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import static org.mockito.Mockito.*

class Neo4jMessageStoreSpec extends Specification implements TestData {
    @Mock
    Neo4jMessageRepository messageRepository
    @Mock
    HistoryService historyService
    @Mock
    Random ran
    @InjectMocks
    Neo4jMessageStore neo4jMessageStore

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test get Start Advice For User"() {
        given:
        def userId = "testId"
        when(historyService.getPastAdvicesIds(new User(userId))).thenReturn(["4"] as Set<String>)
        when(messageRepository.getStartNodesThatWereNotUsed([4L] as Set<Long>))
                .thenReturn(validDtoList(3, { id -> validMessageDto(id, "testMsg${id}")}) as List<MessageDto>)
        when(ran.nextInt(3)).thenReturn(1)

        when:
        Advice result = neo4jMessageStore.getStartMessageForUser(new User(userId))

        then:
        result.id == '2'
        result.msg == 'testMsg2'
    }

    def "test get Start Advice For User if no advices left"() {
        given:
        def userId = "testId"
        when(historyService.getPastAdvicesIds(new User(userId))).thenReturn(["4"] as Set<String>)
        when(messageRepository.getStartNodesThatWereNotUsed([4L] as Set<Long>)).thenReturn([])

        when:
        neo4jMessageStore.getStartMessageForUser(new User(userId))

        then:
        def ex = thrown NoNewAdviceForUserException
        ex.message == "All advices have been used for user: ${userId}"
    }
}