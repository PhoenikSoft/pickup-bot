package com.phoeniksoft.pickupbot.infrastructure.scheduling

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.mockito.Mockito.when

class ScheduledTasksSpec extends Specification {

    @Mock
    AdviceStore adviceStore;

    @InjectMocks
    ScheduledTasks scheduledTasks;

    def setup() {
        MockitoAnnotations.initMocks(this);
    }

    def "test hook Neo4j"() {
        given:
        when(adviceStore.getNodesCount()).thenReturn(1L)

        when:
        scheduledTasks.hookNeo4j()

        then:
        Mockito.verify(adviceStore, Mockito.times(1))
    }
}
