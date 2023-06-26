package ru.otus.service;

import java.io.InputStream;
import java.util.List;

public interface ResourceExtractor {

    InputStream getDataFromResourceFile();

    List<String> getKeyFromResource(InputStream inputStream);
}
