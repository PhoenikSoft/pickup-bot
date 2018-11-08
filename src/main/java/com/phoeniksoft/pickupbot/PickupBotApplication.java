package com.phoeniksoft.pickupbot;

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
        Neo4jAdviceRepository neo4JAdviceRepository = app.getBean(Neo4jAdviceRepository.class);

        Optional<AdviceDto> cameras = neo4JAdviceRepository.getNextAdviceByUserAnswer(3L, "NO");
        neo4JAdviceRepository.getAllStartNodes().forEach(System.out::println);
        System.out.println(cameras.get());
        AdviceDto defaultNode = neo4JAdviceRepository.getDefaultNode();
        System.out.println(defaultNode);

        SpringApplication.exit(app);
    }
}
