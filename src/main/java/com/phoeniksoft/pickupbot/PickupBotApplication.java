package com.phoeniksoft.pickupbot;

import com.phoeniksoft.pickupbot.domain.context.ContextFiller;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserCommand;
import com.phoeniksoft.pickupbot.domain.core.UserMessage;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PickupBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(PickupBotApplication.class, args);
        ContextFiller contextFiller = app.getBean(ContextFiller.class);

        UserQuery userQuery = UserQuery.builder().command(UserCommand.GET_START_ADVICE).message(new UserMessage("test")).build();
        UserContext userContext = contextFiller.fillContext(userQuery);
        System.out.println(userContext);

        SpringApplication.exit(app);
    }
}
