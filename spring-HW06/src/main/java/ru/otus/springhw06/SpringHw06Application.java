package ru.otus.springhw06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringHw06Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringHw06Application.class, args);
	}
}
