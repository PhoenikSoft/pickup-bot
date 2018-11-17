package com.phoeniksoft.pickupbot.app.config;

import com.phoeniksoft.pickupbot.domain.context.ContextFiller;
import com.phoeniksoft.pickupbot.domain.context.interceptors.ContextInterceptorsFiller;
import com.phoeniksoft.pickupbot.domain.context.interceptors.UserCommandInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ContextConfig {

    @Bean
    public UserCommandInterceptor userCommandInterceptor() {
        return new UserCommandInterceptor();
    }

    @Bean
    public ContextFiller contextFiller(UserCommandInterceptor userCommandInterceptor) {
        return new ContextInterceptorsFiller(Arrays.asList(userCommandInterceptor));
    }
}
