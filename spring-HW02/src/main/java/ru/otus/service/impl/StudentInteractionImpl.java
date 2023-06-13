package ru.otus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.config.ApplicationConfig;
import ru.otus.service.StudentInteraction;

import java.util.InputMismatchException;
import java.util.Scanner;

@Service
public class StudentInteractionImpl implements StudentInteraction {

    private static final Logger log = LogManager.getLogger(StudentInteractionImpl.class);
    private final Scanner scanner;
    private final double boundary;

    public StudentInteractionImpl(ApplicationConfig config) {
        this.boundary = config.getBoundary();
        scanner = config.getScannerSystemIn();

    }

    public String getName() {
        boolean correct = false;
        String name = null;
        while (!correct) {
            try {
                name = scanner.next("[a-zA-Z]+");
                correct = true;
            } catch (InputMismatchException e) {
                log.error("Incorrect. Please try again");
                scanner.next();
            }
        }
        return name;
    }

    @Override
    public double getBoundary() {
        return boundary;
    }
}
