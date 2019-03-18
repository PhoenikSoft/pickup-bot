package com.phoeniksoft.pickupbot.infrastructure.scheduling;

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Set up scheduled asynchronous tasks.
 */
@Component
@Slf4j
public class ScheduledTasks {

    private final AdviceStore adviceStore;

    @Autowired
    public ScheduledTasks(AdviceStore adviceStore) {
        this.adviceStore = adviceStore;
    }

    /**
     * We should hook and call Neo4j database, because it sleeps every 30 minutes on Heroku
     * that causes database interaction errors.
     */
    @Scheduled(fixedRate = 1740000)
    public void hookNeo4j() {
        log.info("Scheduled task is executing. {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        adviceStore.getNodesCount();
    }
}
