package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.config.ApplicationConfig;
import ru.otus.model.Student;
import ru.otus.model.TestBlock;
import ru.otus.service.IOService;
import ru.otus.service.TestBlockCreator;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.utils.Constants.*;

@DisplayName("Проверяем, что блок тестов")
@ExtendWith(SpringExtension.class)
class TestBlockCreatorImplTest {

    ResourceBundleMessageSource messageSource;
    @Mock
    ApplicationConfig config;
    @Mock
    IOService ioService;
    private Student student;
    private List<String> keyAndNumberOfAnswersList;

    @BeforeEach
    void setUp() throws IOException {
        messageSource = new ResourceBundleMessageSource();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream resourceAsStream = loader.getResourceAsStream("messages_pl.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        messageSource.setCommonMessages(properties);
        Mockito.when(ioService.askForName()).thenReturn("Ivan");
        Mockito.when(ioService.askForLastName()).thenReturn("Ivanov");
        student = new Student(ioService.askForName(), ioService.askForLastName());
        keyAndNumberOfAnswersList = Arrays.asList(
                    "question.1",
                    "question.2",
                    "question.3",
                    "question.4",
                    "question.5"
        );
    }

    @Test
    @DisplayName("номеров вопросов равен вопросам под этими номерами")
    void shouldBeEqualsNumberOfQuestions() {
        TestBlockCreator testBlockCreator = new TestBlockCreatorImpl(messageSource, config);
        List<TestBlock> testBlocks = testBlockCreator.createTestBlock(keyAndNumberOfAnswersList, student);

        assertEquals(testBlocks.get(0).getQuestion(), messageSource.getMessage("question.1", null, new Locale("pl")));
        assertEquals(testBlocks.get(1).getQuestion(), messageSource.getMessage("question.2", null, new Locale("pl")));
        assertEquals(testBlocks.get(2).getQuestion(), messageSource.getMessage("question.3", null, new Locale("pl")));
        assertEquals(testBlocks.get(3).getQuestion(), messageSource.getMessage("question.4", null, new Locale("pl")));
        assertEquals(testBlocks.get(4).getQuestion(), messageSource.getMessage("question.5", null, new Locale("pl")));

    }

    @Test
    @DisplayName("номеров ответов равен ответам под этими номерами")
    void shouldBeEqualsNumberOfAnswers(){
        TestBlockCreator testBlockCreator = new TestBlockCreatorImpl(messageSource, config);
        List<TestBlock> testBlocks = testBlockCreator.createTestBlock(keyAndNumberOfAnswersList, student);
        assertEquals(testBlocks.get(0).getAnswers(), getAnswers(messageSource.getMessage("answer.1", null, new Locale("pl"))));
        assertEquals(testBlocks.get(1).getAnswers(), getAnswers(messageSource.getMessage("answer.2", null, new Locale("pl"))));
        assertEquals(testBlocks.get(2).getAnswers(), getAnswers(messageSource.getMessage("answer.3", null, new Locale("pl"))));
        assertEquals(testBlocks.get(3).getAnswers(), getAnswers(messageSource.getMessage("answer.4", null, new Locale("pl"))));
        assertEquals(testBlocks.get(4).getAnswers(), getAnswers(messageSource.getMessage("answer.5", null, new Locale("pl"))));
    }


    private List<String> getAnswers(String answer) {
        List<String> answersList;
        String[] split = answer.split(COMMA);
        if (split.length == 0) {
            return new ArrayList<>();
        } else {
            answersList = new ArrayList<>(Arrays.asList(split));
        }
        return answersList;
    }
}
