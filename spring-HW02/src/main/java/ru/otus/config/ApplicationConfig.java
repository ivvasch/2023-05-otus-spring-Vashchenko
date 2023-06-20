package ru.otus.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.model.ResourceFile;

import java.util.Scanner;

@Configuration
@PropertySource("classpath:application.properties")
@Data
public class ApplicationConfig {
    @Value("${application.boundary}")
    private double boundary;

    @Bean
    public ResourceFile resourceFile(@Value("${application.resourceFile}") String resourceFile) {
        return new ResourceFile(resourceFile);
    }

    @Bean
    public Scanner getScannerSystemIn() {
        return new Scanner(System.in);
    }
}
