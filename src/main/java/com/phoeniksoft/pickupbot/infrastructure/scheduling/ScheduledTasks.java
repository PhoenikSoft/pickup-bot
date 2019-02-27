package com.phoeniksoft.pickupbot.infrastructure.scheduling;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

    private final AdviceStore adviceStore;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    @Autowired
    public ScheduledTasks(AdviceStore adviceStore) {
        this.adviceStore = adviceStore;
    }

    @Scheduled(fixedRate = 120000)
    public void reportCurrentTime() {
        adviceStore.getNodesCount();
        log.info("Scheduled task is executed. {}", dateFormat.format(new Date()));
    }


}
