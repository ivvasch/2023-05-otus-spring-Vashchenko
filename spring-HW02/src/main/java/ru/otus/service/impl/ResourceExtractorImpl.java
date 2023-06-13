package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.model.ResourceFile;
import ru.otus.service.ResourceExtractor;

import java.io.InputStream;

@Service
public class ResourceExtractorImpl implements ResourceExtractor {

    private final ResourceFile resourceFile;

    public ResourceExtractorImpl(ResourceFile resourceFile) {
        this.resourceFile = resourceFile;
    }

    @Override
    public InputStream getDataFromResourceFile() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader.getResourceAsStream(resourceFile.name());
    }
}
