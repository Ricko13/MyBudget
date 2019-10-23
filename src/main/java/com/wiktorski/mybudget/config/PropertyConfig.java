package com.wiktorski.mybudget.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

//@ConfigurationProperties(prefix = "custom")
//@Validated

@Component
@EnableConfigurationProperties(PropertyConfig.class)
@ConfigurationProperties("custom")
public class PropertyConfig {

    private boolean devMode;

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }
}
