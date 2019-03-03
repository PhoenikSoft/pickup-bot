package com.phoeniksoft.pickupbot.infrastructure.scheduling

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore
import spock.lang.Specification

class ScheduledTasksSpec extends Specification {

    AdviceStore adviceStore = Mock()

    def scheduledTasks = new ScheduledTasks(adviceStore)

    def "test hook Neo4j"() {
        when:
        scheduledTasks.hookNeo4j()

        then:
        1 * adviceStore.getNodesCount()
    }
}
