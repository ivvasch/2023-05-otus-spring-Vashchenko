package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.service.ResourceExtractor;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
@SpringBootTest
@DisplayName("При получении данных")
class ResourceExtractorImplTest {

    @Autowired
    ResourceExtractor extractor;
    InputStream inputStream;
    @BeforeEach
    void setUp() {
        inputStream = extractor.getDataFromResourceFile();
    }

    @Test
    @DisplayName("файл ресурсов не равен null")
    void getDataFromResourceFile() {
        assertNotEquals(null, inputStream);
    }


    @Test
    @DisplayName("файл ресурсов содержит 5 строк")
    void getKeyFromResource() {
        List<String> keyFromResource = extractor.getKeyFromResource(inputStream);
        assertEquals(5, keyFromResource.size());
    }
}
