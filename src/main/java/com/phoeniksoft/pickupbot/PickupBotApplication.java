package com.phoeniksoft.pickupbot;

import com.phoeniksoft.pickupbot.infrastructure.neo4j.AdviceDto;
import com.phoeniksoft.pickupbot.infrastructure.neo4j.Neo4jAdviceDao;
import com.phoeniksoft.pickupbot.infrastructure.neo4j.Neo4jMarker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.Optional;

@SpringBootApplication
@EnableNeo4jRepositories("com.phoeniksoft.pickupbot.infrastructure.neo4j")
public class PickupBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(PickupBotApplication.class, args);
        Neo4jAdviceDao neo4jAdviceDao = app.getBean(Neo4jAdviceDao.class);

        Optional<AdviceDto> cameras = neo4jAdviceDao.findByTitle("Cameras");
//        allAdvices.forEach(System.out::println);
        System.out.println(cameras.get());
    }
}
