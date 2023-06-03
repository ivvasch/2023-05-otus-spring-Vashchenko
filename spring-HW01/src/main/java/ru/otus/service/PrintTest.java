package ru.otus.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.model.TestBlock;

import java.util.List;

public class PrintTest {
    private static final Logger log = LogManager.getLogger(PrintTest.class);
    public void printResult(List<TestBlock> blocks) {
        for (TestBlock block : blocks) {
            log.warn("Attention, the question: {}", block.getQuestion());
            for (String answer : block.getAnswers()) {
                log.info("One of the variant of Answer: [{}]", answer);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.error("Error when thread was sleep");
                throw new RuntimeException(e);
            }

        }
    }
}
