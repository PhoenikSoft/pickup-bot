package com.phoeniksoft.pickupbot;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.domain.advisor.NextAdviceParams;
import com.phoeniksoft.pickupbot.infrastructure.neo4j.AdviceDto;
import com.phoeniksoft.pickupbot.infrastructure.neo4j.Neo4jAdviceRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class PickupBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(PickupBotApplication.class, args);
        AdviceStore adviceStore = app.getBean(AdviceStore.class);

        Optional<Advice> advice = adviceStore.getById("3");
        System.out.println(advice.get());

        NextAdviceParams params = new NextAdviceParams();
        params.put(NextAdviceParams.USER_ANSWER_PARAM, "YES");
        Optional<Advice> nextAdvice = adviceStore.getNextAdvice(advice.get().getId(), params);
        System.out.println(nextAdvice.get());

        Optional<Advice> startAdvice = adviceStore.getStartAdvice();
        System.out.println(startAdvice.get());

        Advice defaultAdvice = adviceStore.getDefaultAdvice();
        System.out.println(defaultAdvice);

        SpringApplication.exit(app);
    }
}
