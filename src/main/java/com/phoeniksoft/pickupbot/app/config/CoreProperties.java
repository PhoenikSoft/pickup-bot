package com.phoeniksoft.pickupbot.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "core")
@Data
public class CoreProperties {

    private int proposalsLimit;
}
