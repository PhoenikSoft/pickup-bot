package com.phoeniksoft.pickupbot;

import com.phoeniksoft.pickupbot.domain.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PickupBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(PickupBotApplication.class, args);
        try {
            PickupBotApi pickupBotApi = app.getBean(PickupBotApi.class);

            UserQuery userQuery = UserQuery.builder()
                    .command(UserCommand.GET_START_ADVICE)
                    .build();
            userQuery.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, "testE");
            UserAdvice advice = pickupBotApi.getAdvice(userQuery);
            System.out.println("Start advice: " + advice);

            userQuery = UserQuery.builder()
                    .command(UserCommand.GET_NEXT_ADVICE)
                    .message(new UserMessage("YES"))
                    .build();
            userQuery.getSpecificParams().put(UserQueryParams.USER_ID_PARAM, "testE");
            advice = pickupBotApi.getAdvice(userQuery);
            System.out.println("Next advice: " + advice);
        } finally {
            SpringApplication.exit(app);
        }
    }
}
