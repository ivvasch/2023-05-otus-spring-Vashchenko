package ru.otus.service;

import ru.otus.model.Student;
import ru.otus.model.TestBlock;

import java.util.List;

public interface TestBlockCreator {

    List<TestBlock> createTestBlock(List<String> keyAndNumberOfAnswersList, Student student);
}
