package ru.otus.service;

import ru.otus.model.TestBlock;

import java.util.List;

public interface IOService {

    String askForName();

    String askForLastName();

    void printQuestionAnswers(List<TestBlock> blocks);
}
