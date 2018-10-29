package com.phoeniksoft.pickupbot;

import com.phoeniksoft.pickupbot.app.PickupBotApi;
import com.phoeniksoft.pickupbot.model.UserAdvice;
import com.phoeniksoft.pickupbot.model.UserCommand;
import com.phoeniksoft.pickupbot.model.UserQuery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;

@SpringBootApplication
public class PickupBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(PickupBotApplication.class, args);
        PickupBotApi pickupBotApi = app.getBean(PickupBotApi.class);

        UserAdvice advice = pickupBotApi.getAdvice(UserQuery.builder().command(UserCommand.GET_START_ADVICE).build());
        System.out.println(advice.getMsg());

        HashMap<String, Object> params = new HashMap<>(1);
        params.put("prevAdviceId", advice.getPayload().get("adviceId"));
        advice = pickupBotApi.getAdvice(UserQuery.builder().command(UserCommand.GET_NEXT_ADVICE).specificParams(params).build());
        System.out.println(advice.getMsg());

        advice = pickupBotApi.getAdvice(UserQuery.builder().command(UserCommand.GET_START_ADVICE).build());
        System.out.println(advice.getMsg()); // Could be another advice
    }
}
