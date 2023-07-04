package ru.otus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.exception.TestException;
import ru.otus.model.ResourceFile;
import ru.otus.service.ResourceExtractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceExtractorImpl implements ResourceExtractor {

    private static final Logger log = LogManager.getLogger(ResourceExtractorImpl.class);
    private final ResourceFile resourceFile;

    public ResourceExtractorImpl(ResourceFile resourceFile) {
        this.resourceFile = resourceFile;
    }

    @Override
    public InputStream getDataFromResourceFile() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader.getResourceAsStream(resourceFile.name());
    }

    @Override
    public List<String> getKeyFromResource(InputStream inputStream) {
        List<String> keyAndNumberOfAnswersList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                keyAndNumberOfAnswersList.add(line);
            }
        } catch (IOException e) {
            log.error("Error when we read inputStream from source file. Message is {}", e.getMessage());
            throw new TestException(e);
        }
        return keyAndNumberOfAnswersList;
    }
}
