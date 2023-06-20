package ru.otus.exception;

import java.io.IOException;

public class TestException extends RuntimeException {

    TestException() {
        super();
    }

    TestException(String message) {
        super(message);
    }

    public TestException(IOException e) {
        super(e);
    }
}
