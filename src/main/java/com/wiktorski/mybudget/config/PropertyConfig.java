package com.wiktorski.mybudget.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.sql.Connection;

//@ConfigurationProperties(prefix = "custom")
//@Validated

@Component
@EnableConfigurationProperties(DevMode.class)
@ConfigurationProperties("custom")
public class DevMode {

    private boolean devMode;

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }
}
