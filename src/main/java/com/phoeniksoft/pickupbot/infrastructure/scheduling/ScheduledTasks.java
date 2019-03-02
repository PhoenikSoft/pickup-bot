package com.phoeniksoft.pickupbot.infrastructure.scheduling;

import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class ScheduledTasks {

    private final AdviceStore adviceStore;

    @Autowired
    public ScheduledTasks(AdviceStore adviceStore) {
        this.adviceStore = adviceStore;
    }

    @Scheduled(fixedRate = 1740000)
    public void hookNeo4j() {
        adviceStore.getNodesCount();
        log.info("Scheduled task is executed. {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
