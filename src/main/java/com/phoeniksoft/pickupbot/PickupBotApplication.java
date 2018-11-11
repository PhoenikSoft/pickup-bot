package com.phoeniksoft.pickupbot;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.advisor.Advisor;
import com.phoeniksoft.pickupbot.domain.context.AdviceType;
import com.phoeniksoft.pickupbot.domain.context.UserAnswer;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PickupBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(PickupBotApplication.class, args);
        Advisor adviceStore = app.getBean(Advisor.class);

        UserContext context = UserContext.builder().userIntent(AdviceType.START_MESSAGE).build();
        Advice prevAdvice = adviceStore.getAdvice(context);
        System.out.println("START ADVICE: " + prevAdvice);

        context = UserContext.builder().userIntent(AdviceType.NEXT_STEP).userAnswer(UserAnswer.YES).build();
        context.getPayload().put(UserContext.ContextPayload.PREV_ADVICE_PARAM, prevAdvice.getId());
        System.out.println("NEXT ADVICE(if helped): " + adviceStore.getAdvice(context));

        context = UserContext.builder().userIntent(AdviceType.NEXT_STEP).userAnswer(UserAnswer.NO).build();
        context.getPayload().put(UserContext.ContextPayload.PREV_ADVICE_PARAM, prevAdvice.getId());
        System.out.println("NEXT ADVICE(if not helped): " + adviceStore.getAdvice(context));

        context = UserContext.builder().userIntent(AdviceType.DATE_INVITATION).build();
        System.out.println("NOT SUPPORTED: " + adviceStore.getAdvice(context));

        SpringApplication.exit(app);
    }
}
