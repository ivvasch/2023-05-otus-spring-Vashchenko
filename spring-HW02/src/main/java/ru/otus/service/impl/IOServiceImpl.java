package ru.otus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.config.ApplicationConfig;
import ru.otus.model.TestBlock;
import ru.otus.service.IOService;
import ru.otus.service.StudentInteraction;

import java.util.List;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private static final Logger log = LogManager.getLogger(IOServiceImpl.class);
    private final Scanner scanner;
    private final StudentInteraction interaction;

    public IOServiceImpl(StudentInteraction interaction, ApplicationConfig config) {
        this.interaction = interaction;
        scanner = config.getScannerSystemIn();
    }

    @Override
    public String askForName() {
        log.warn("What's your name");
        return interaction.getName();
    }

    @Override
    public String askForLastName() {
        log.warn("What's your last name");
        return interaction.getName();
    }

    @Override
    public void printQuestionAnswers(List<TestBlock> blocks) {
        for (TestBlock block : blocks) {
            log.warn("Attention, the question: {}", block.getQuestion());
            log.info("Choose one of the variant of Answer:");
            int count = 0;
            for (String answer : block.getAnswers()) {
                log.info("{} answer: [{}]",++count, answer);
            }
            log.warn("Enter digit of answer");
            int answerOfStudent = scanner.nextInt();
            block.setAnswer(answerOfStudent);
        }
        printResult(blocks);
    }

    private void printResult(List<TestBlock> blocks) {
        int count = 0;
        for (TestBlock block : blocks) {
            if ((block.getAnswer() / block.getCorrectAnswer()) == 1) {
                count++;
            }
        }
        double percentOfCorrectAnswers = count / (blocks.size() * 1.0);
        if (percentOfCorrectAnswers > interaction.getBoundary()) {
            log.info("Correct test is done. Percent of correct answers is {}", percentOfCorrectAnswers);
        } else {
            log.error("You failed the test. Percent of correct answers is {}", percentOfCorrectAnswers);
        }
        scanner.close();
    }
}
