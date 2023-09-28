package ru.otus.metrics;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OwnHealthcheck implements HealthIndicator {

    Random random = new Random();
    @Override
    public Health health() {
        int count = random.nextInt(1, 999);
        if (count % 2 > 0) {
            return Health.down().status(Status.DOWN).withDetail("message", "Odd").build();
        }
        return Health.down().status(Status.UP).withDetail("message", "Even").build();
    }
}
