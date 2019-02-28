package com.phoeniksoft.pickupbot.infrastructure.neo4j

import com.phoeniksoft.pickupbot.common.TestData
import com.phoeniksoft.pickupbot.domain.advisor.Advice
import com.phoeniksoft.pickupbot.domain.advisor.AdviceType
import com.phoeniksoft.pickupbot.domain.advisor.NextAdviceParams
import com.phoeniksoft.pickupbot.domain.advisor.exception.NoNewAdviceForUserException
import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.history.HistoryService
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.mockito.Mockito.when

class Neo4jAdviceStoreSpec extends Specification implements TestData {
    @Mock
    Neo4jAdviceRepository adviceRepository
    @Mock
    HistoryService historyService
    @Mock
    Random ran
    @InjectMocks
    Neo4jAdviceStore neo4jAdviceStore

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test get By Id"() {
        given:
        when(adviceRepository.findById(1)).thenReturn(Optional.of(validAdviceDto()))

        when:
        def result = neo4jAdviceStore.getById('1').get()

        then:
        result.id == '1'
        result.msg == 'testMsg'
        result.payload.isEmpty()
    }

    def "test get By Id if not found"() {
        given:
        when(adviceRepository.findById(1)).thenReturn(Optional.empty())

        when:
        def result = neo4jAdviceStore.getById('1')

        then:
        !result.isPresent()
    }

    def "test get Start Advice For User"() {
        given:
        def userId = "testId"
        when(historyService.getPastAdvicesIds(new User(userId))).thenReturn(["4"] as Set<String>)
        when(adviceRepository.getNodesByTagThatWereNotUsed("date", [4L] as Set<Long>))
                .thenReturn(validDtoList(3, { id -> validAdviceDto(id, "testMsg${id}") }) as List<AdviceDto>)
        when(ran.nextInt(3)).thenReturn(1)

        when:
        Advice result = neo4jAdviceStore.getAdviceByTypeForUser(AdviceType.DATE, new User(userId))

        then:
        result.id == '2'
        result.msg == 'testMsg2'
    }

    def "test get Start Advice For User if no advices left"() {
        given:
        def userId = "testId"
        when(historyService.getPastAdvicesIds(new User(userId))).thenReturn(["4"] as Set<String>)
        when(adviceRepository.getNodesByTagThatWereNotUsed("profile", [4L] as Set<Long>)).thenReturn([])
        when(adviceRepository.getNodesByTag("profile"))
                .thenReturn(validDtoList(2, { id -> validAdviceDto(id, "testMsg${id}") }) as List<AdviceDto>)
        when(ran.nextInt(2)).thenReturn(1)

        when:
        neo4jAdviceStore.getAdviceByTypeForUser(AdviceType.PROFILE, new User(userId))

        then:
        def ex = thrown NoNewAdviceForUserException
        ex.message == "All advices have been used for user: ${userId}"
        ex.oldAdvice.id == '2'
        ex.oldAdvice.msg == 'testMsg2'
    }

    def "test get Next Advice"() {
        given:
        when(adviceRepository.getNextAdviceByUserAnswer(1, 'testAnswer')).thenReturn(Optional.of(validAdviceDto()))

        when:
        def result = neo4jAdviceStore.getNextAdvice("1",
                new NextAdviceParams([(NextAdviceParams.USER_ANSWER_PARAM): 'testAnswer'])).get()

        then:
        result.id == '1'
        result.msg == 'testMsg'
    }

    def "test get Next Advice if not found"() {
        given:
        when(adviceRepository.getNextAdviceByUserAnswer(1, 'testAnswer')).thenReturn(Optional.empty())

        when:
        def result = neo4jAdviceStore.getNextAdvice("1",
                new NextAdviceParams([(NextAdviceParams.USER_ANSWER_PARAM): 'testAnswer']))

        then:
        !result.isPresent()
    }

    def "test get Default Advice"() {
        given:
        when(adviceRepository.getDefaultNode()).thenReturn(validAdviceDto())

        when:
        def result = neo4jAdviceStore.getDefaultAdvice()

        then:
        result.id == '1'
        result.msg == 'testMsg'
    }

    def "test get Nodes Count"() {
        given:
        def expectedNodesCount = 1L
        when(adviceRepository.getNodesCount()).thenReturn(expectedNodesCount)

        when:
        def nodesCount = neo4jAdviceStore.getNodesCount()

        then:
        expectedNodesCount == nodesCount
    }
}
