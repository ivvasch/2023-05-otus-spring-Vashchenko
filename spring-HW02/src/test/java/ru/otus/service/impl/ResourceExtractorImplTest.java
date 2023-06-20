package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.ResourceFile;
import ru.otus.service.ResourceExtractor;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("При получении данных")
class ResourceExtractorImplTest {

    ResourceFile resourceFile;
    ResourceExtractor extractor;
    InputStream inputStream;
    @BeforeEach
    void setUp() {
        resourceFile = new ResourceFile("test.csv");
        extractor = new ResourceExtractorImpl(resourceFile);

    }

    @Test
    @DisplayName("файл ресурсов не равен null")
    void getDataFromResourceFile() {
        inputStream = extractor.getDataFromResourceFile();
        assertNotEquals(null, inputStream);
    }
}
