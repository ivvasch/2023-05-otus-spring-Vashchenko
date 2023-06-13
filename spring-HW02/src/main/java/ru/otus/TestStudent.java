package ru.otus;

import org.springframework.stereotype.Component;
import ru.otus.model.Student;
import ru.otus.model.TestBlock;
import ru.otus.service.IOService;
import ru.otus.service.ResourceExtractor;
import ru.otus.service.TestBlockCreator;

import java.io.InputStream;
import java.util.List;

@Component
public class TestStudent {
    private final IOService ioService;
    private final ResourceExtractor extractor;
    private final TestBlockCreator creator;

    public TestStudent(IOService ioService, ResourceExtractor extractor, TestBlockCreator creator) {
        this.ioService = ioService;
        this.extractor = extractor;
        this.creator = creator;
    }

    public void start() {
        InputStream dataFromResourceFile = extractor.getDataFromResourceFile();
        Student student = new Student(ioService.askForName(), ioService.askForLastName());
        List<TestBlock> testBlock = creator.createTestBlock(dataFromResourceFile, student);
        ioService.printQuestionAnswers(testBlock);

    }
}
