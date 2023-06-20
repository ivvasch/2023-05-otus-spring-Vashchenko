package ru.otus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.exception.TestException;
import ru.otus.model.Student;
import ru.otus.model.TestBlock;
import ru.otus.service.TestBlockCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class TestBlockCreatorImpl implements TestBlockCreator {

    private static final Logger log = LogManager.getLogger(TestBlockCreatorImpl.class);
    @Override
    public List<TestBlock> createTestBlock(InputStream inputStream, Student student) {
        List<TestBlock> testBlockList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                TestBlock block = new TestBlock();
                String[] split = line.split(";");
                block.setQuestion(split[0]);
                block.setAnswers(getAllAnswers(split));
                block.setCorrectAnswer(getCorrectVariant(split[split.length - 1]));
                block.setStudent(student);
                testBlockList.add(block);
            }
        } catch (IOException e) {
            log.error("Error when we read inputStream. Check resource file.");
            throw new TestException(e);
        }
        return testBlockList;
    }

    private int getCorrectVariant(String s) {
        int correctAnswerNumber = 0;
        if (s.startsWith("[") && s.endsWith("]")) {
            correctAnswerNumber = parseInt(s.substring(1, s.lastIndexOf("]")));
        }
        return correctAnswerNumber;
    }

    private List<String> getAllAnswers(String[] split) {
        List<String> answersList;
        if (split.length == 0) {
            return new ArrayList<>();
        } else {
            answersList = new ArrayList<>(Arrays.asList(split).subList(1, split.length-1));
        }
        return answersList;
    }
}
