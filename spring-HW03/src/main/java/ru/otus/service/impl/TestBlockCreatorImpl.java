package ru.otus.service.impl;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.ApplicationConfig;
import ru.otus.model.Student;
import ru.otus.model.TestBlock;
import ru.otus.service.TestBlockCreator;
import ru.otus.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;
import static ru.otus.utils.Constants.*;

@Service
public class TestBlockCreatorImpl implements TestBlockCreator {

    private final ResourceBundleMessageSource messageSource;
    private final ApplicationConfig config;

    public TestBlockCreatorImpl(ResourceBundleMessageSource messageSource, ApplicationConfig config) {
        this.messageSource = messageSource;
        this.config = config;
    }

    @Override
    public List<TestBlock> createTestBlock(List<String> keyAndNumberOfAnswersList, Student student) {
        List<TestBlock> testBlockList = new ArrayList<>();
        for (String keyAndNumber : keyAndNumberOfAnswersList) {
            TestBlock block = new TestBlock();
            String[] split = keyAndNumber.split(Constants.SEMICOLON);
            String message = messageSource.getMessage(split[0], null, config.getLocale());
            block.setQuestion(message);
            block.setAnswers(getAllAnswers(split[0]));
            block.setCorrectAnswer(getCorrectVariant(split[split.length - 1]));
            block.setStudent(student);
            testBlockList.add(block);
        }
        return testBlockList;
    }

    private int getCorrectVariant(String s) {
        int correctAnswerNumber = 0;
        if (s.startsWith(SQUARE_BRACKET) && s.endsWith(SQUARE_REVERSE_BRACKET)) {
            correctAnswerNumber = parseInt(s.substring(1, s.lastIndexOf(SQUARE_REVERSE_BRACKET)));
        }
        return correctAnswerNumber;
    }

    private List<String> getAllAnswers(String key) {
        List<String> answersList;
        int index = key.lastIndexOf(DOT);
        var answer = ANSWER + key.substring(index);
        String message = messageSource.getMessage(answer, null, config.getLocale());
        String[] split = message.split(COMMA);
        if (split.length == 0) {
            return new ArrayList<>();
        } else {
            answersList = new ArrayList<>(Arrays.asList(split));
        }
        return answersList;
    }
}
