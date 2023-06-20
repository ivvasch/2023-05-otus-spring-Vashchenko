package ru.otus.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.model.Student;
import ru.otus.model.TestBlock;
import ru.otus.service.IOService;
import ru.otus.service.TestBlockCreator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Проверяем, что блок тестов")
@ExtendWith(MockitoExtension.class)
class TestBlockCreatorImplTest {

    private static final String questions = """
            What is framework?;Pretty name;Is that a program?;Software platform;[3]
            What is Spring?;It's spring;It's cool framework;I don't understand yet;What about you?;[2]
            Why I should use spring?;Because it works for me;Configure dependency;I usually go for a walk in the spring;[2]
            What is a new version of spring is?;2;3;5;6;[4]
            What is a new features in the new version of spring?;New features in xml configuration;Add Annotation configuration;[2]"
            """;
    @Mock
    IOService ioService;

    @Test
    @DisplayName("должен содержать 5 вопросов")
    void shouldContainFiveQuestion() {
        Mockito.when(ioService.askForName()).thenReturn("Ivan");
        Mockito.when(ioService.askForLastName()).thenReturn("Ivanov");
        Student student = new Student(ioService.askForName(), ioService.askForLastName());
        TestBlockCreator testBlockCreator = new TestBlockCreatorImpl();
        InputStream inputStream = new ByteArrayInputStream((questions).getBytes());
        List<TestBlock> testBlocks = testBlockCreator.createTestBlock(inputStream, student);
        assertEquals(5, testBlocks.size());
    }
}
