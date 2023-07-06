package ru.otus.model;

import lombok.Data;

import java.util.List;

@Data
public final class TestBlock {
    private String question;
    private List<String> answers;
    private Student student;
    private int answer;
    private int correctAnswer;
}
