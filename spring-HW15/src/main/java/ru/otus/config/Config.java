package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import ru.otus.model.autobot.Autobot;
import ru.otus.model.machine.Machine;
import ru.otus.service.TransformService;

@Configuration

public class Config {

    private final TransformService transformService;

    public Config(TransformService transformService) {
        this.transformService = transformService;
    }

    @Bean
    public IntegrationFlow machineChanel() {
        return f -> f.split()
                .channel("fromSplitToTransform")
                .<Machine, Autobot>transform(transformService::transform)
                .aggregate();


    }

}
