package ru.otus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.ApplicationConfig;
import ru.otus.model.TestBlock;
import ru.otus.service.IOService;
import ru.otus.service.StudentInteraction;

import java.util.List;
import java.util.Scanner;

import static ru.otus.utils.Constants.*;

@Service
public class IOServiceImpl implements IOService {

    private static final Logger log = LogManager.getLogger(IOServiceImpl.class);
    private final Scanner scanner;
    private final StudentInteraction interaction;
    private final ResourceBundleMessageSource messageSource;
    private final ApplicationConfig config;

    public IOServiceImpl(StudentInteraction interaction, ResourceBundleMessageSource messageSource, ApplicationConfig config) {
        this.interaction = interaction;
        this.config = config;
        scanner = config.getScannerSystemIn();
        this.messageSource = messageSource;
    }

    @Override
    public String askForName() {
        log.warn(messageSource.getMessage(NAME, null, config.getLocale()));
        return interaction.getName();
    }

    @Override
    public String askForLastName() {
        log.warn(messageSource.getMessage(LAST_NAME, null, config.getLocale()));
        return interaction.getName();
    }

    @Override
    public void printQuestionAnswers(List<TestBlock> blocks) {
        for (TestBlock block : blocks) {
            log.warn("{} {}",messageSource.getMessage(QUESTION, null, config.getLocale()), block.getQuestion());
            log.info(messageSource.getMessage(VARIANT, null, config.getLocale()));
            int count = 0;
            for (String answer : block.getAnswers()) {
                log.info("{} {} [{}]",++count, messageSource.getMessage(ANSWER, null, config.getLocale()),answer);
            }
            log.warn(messageSource.getMessage(ENTER_DIGIT, null, config.getLocale()));
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
            log.info("{} {}", messageSource.getMessage(CORRECT, null, config.getLocale()), percentOfCorrectAnswers);
            log.warn("To quit from test input stop.");
        } else {
            log.error("{} {}", messageSource.getMessage(FAILED, null, config.getLocale()), percentOfCorrectAnswers);
            log.warn("To quit from test input stop.");
        }
    }
}
