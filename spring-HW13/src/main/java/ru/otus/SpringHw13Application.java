package ru.otus;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableMongock
@EnableWebSecurity
@EnableMongoRepositories
@SpringBootApplication
public class SpringHw13Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringHw13Application.class, args);
    }

}
