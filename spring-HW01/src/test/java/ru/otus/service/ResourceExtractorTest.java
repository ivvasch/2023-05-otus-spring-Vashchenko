package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.model.TestBlock;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"/spring-context.xml"})
class ResourceExtractorTest {

    private InputStream is;

    @Autowired
    private ResourceExtractor resourceExtractor;

    @BeforeEach
    void setUp() {

        is = new ByteArrayInputStream("What is Spring?;It's spring;It's cool framework;I don't understand yet;What about you?".getBytes());
    }

    @Test
    void shouldCreateTestBlock() {
        List<TestBlock> testBlock = resourceExtractor.createTestBlock(is);
        List<String> list = new ArrayList<>();
        list.add("It's spring");
        list.add("It's cool framework");
        list.add("I don't understand yet");
        list.add("What about you?");

        assertIterableEquals(list, testBlock.get(0).getAnswers());
    }
}
