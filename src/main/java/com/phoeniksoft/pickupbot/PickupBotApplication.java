package com.phoeniksoft.pickupbot;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.core.user.User;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import com.phoeniksoft.pickupbot.domain.history.UserHistory;
import com.phoeniksoft.pickupbot.infrastructure.neo4j.AdviceDto;
import com.phoeniksoft.pickupbot.infrastructure.neo4j.Neo4jAdviceRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PickupBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(PickupBotApplication.class, args);
        try {
            HistoryService historyService = app.getBean(HistoryService.class);
            User user = new User("1123e394-a38f-4baf-9d34-23a12e39cacc");
            Advice advice = Advice.builder().id("3").build();
            UserHistory userHistory = historyService.saveHistory(user, advice);
            System.out.println(userHistory);

            Neo4jAdviceRepository neo4jAdviceRepository = app.getBean(Neo4jAdviceRepository.class);
            AdviceDto adviceDto = new AdviceDto();
            adviceDto.setMsg("testE");
            AdviceDto savedAdvice = neo4jAdviceRepository.save(adviceDto);
            System.out.println(savedAdvice);

        } finally {
            SpringApplication.exit(app);
        }
    }
}
