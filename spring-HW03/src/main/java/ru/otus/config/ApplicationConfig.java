package ru.otus.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.model.ResourceFile;

import java.util.Locale;
import java.util.Scanner;

@Configuration
@Data
public class ApplicationConfig {
    @Value("${application.boundary}")
    private double boundary;
    @Value("${application.locale}")
    private Locale locale;

    @Bean
    public ResourceFile resourceFile(@Value("${application.resourceFile}") String resourceFile) {
        return new ResourceFile(resourceFile);
    }

    @Bean
    public Scanner getScannerSystemIn() {
        return new Scanner(System.in);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}

