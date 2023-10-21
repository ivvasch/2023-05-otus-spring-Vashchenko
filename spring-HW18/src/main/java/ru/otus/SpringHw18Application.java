package ru.otus;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@EnableMongoRepositories
@EnableCircuitBreaker
@EnableHystrixDashboard
@SpringBootApplication
public class SpringHw18Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringHw18Application.class, args);
    }

}
