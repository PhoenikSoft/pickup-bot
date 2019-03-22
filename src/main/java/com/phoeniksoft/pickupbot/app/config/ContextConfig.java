package com.phoeniksoft.pickupbot.app.config;

import com.phoeniksoft.pickupbot.domain.context.ContextFiller;
import com.phoeniksoft.pickupbot.domain.context.ContextInterceptorsFiller;
import com.phoeniksoft.pickupbot.domain.context.interceptors.*;
import com.phoeniksoft.pickupbot.domain.core.user.UserStore;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Bean configuration of the context filler module.
 */
@Configuration
public class ContextConfig {

    @Bean
    public UserCommandInterceptor userCommandInterceptor() {
        return new UserCommandInterceptor();
    }

    @Bean
    public UserAnswerInterceptor userAnswerInterceptor() {
        return new UserAnswerInterceptor();
    }

    @Bean
    public UserInfoInterceptor userInfoInterceptor(UserStore userStore) {
        return new UserInfoInterceptor(userStore);
    }

    @Bean
    public PreviousAdviceInterceptor previousAdviceInterceptor(HistoryService historyService) {
        return new PreviousAdviceInterceptor(historyService);
    }

    @Bean
    public ContextFiller contextFiller(UserCommandInterceptor userCommandInterceptor,
                                       UserAnswerInterceptor userAnswerInterceptor,
                                       UserInfoInterceptor userInfoInterceptor,
                                       PreviousAdviceInterceptor previousAdviceInterceptor) {
        List<ContextInterceptor> interceptors = Arrays.asList(userCommandInterceptor,
                userAnswerInterceptor,
                userInfoInterceptor,
                previousAdviceInterceptor);
        interceptors.sort(Comparator.comparingInt(ContextInterceptor::priority).reversed());
        return new ContextInterceptorsFiller(interceptors);
    }
}
