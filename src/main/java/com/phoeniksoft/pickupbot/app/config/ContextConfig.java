package com.phoeniksoft.pickupbot.app.config;

import com.phoeniksoft.pickupbot.domain.context.ContextFiller;
import com.phoeniksoft.pickupbot.domain.context.interceptors.*;
import com.phoeniksoft.pickupbot.domain.core.user.UserStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

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
    public ContextFiller contextFiller(UserCommandInterceptor userCommandInterceptor,
                                       UserAnswerInterceptor userAnswerInterceptor,
                                       UserInfoInterceptor userInfoInterceptor) {
        List<ContextInterceptor> interceptors = Arrays.asList(userCommandInterceptor,
                userAnswerInterceptor,
                userInfoInterceptor);
        return new ContextInterceptorsFiller(interceptors);
    }
}
