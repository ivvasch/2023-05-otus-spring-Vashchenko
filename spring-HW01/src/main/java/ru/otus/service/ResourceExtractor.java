package ru.otus.service;

import ru.otus.model.ResourceFile;
import ru.otus.model.TestBlock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourceExtractor {

    private final ResourceFile resourceFile;

    public ResourceExtractor(ResourceFile resourceFile) {
        this.resourceFile = resourceFile;
    }

    public InputStream getDataFromResourceFile() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader.getResourceAsStream(resourceFile.name());
    }

    public List<TestBlock> createTestBlock(InputStream inputStream) {
        List<TestBlock> testBlockList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                TestBlock block = new TestBlock();
                String[] split = line.split(";");
                block.setQuestion(split[0]);
                block.setAnswers(getAllAnswers(split));
                testBlockList.add(block);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return testBlockList;
    }

    private List<String> getAllAnswers(String[] split) {
        List<String> answersList;
        if (split.length == 0) {
            return new ArrayList<>();
        } else {
            answersList = new ArrayList<>(Arrays.asList(split).subList(1, split.length));
        }
        return answersList;
    }
}
