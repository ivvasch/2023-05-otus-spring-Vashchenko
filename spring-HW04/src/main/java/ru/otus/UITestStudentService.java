package ru.otus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.model.Student;
import ru.otus.model.TestBlock;
import ru.otus.service.IOService;
import ru.otus.service.ResourceExtractor;
import ru.otus.service.TestBlockCreator;

import java.io.InputStream;
import java.util.List;

@ShellComponent
public class UITestStudentService {

    private static final Logger log = LogManager.getLogger(UITestStudentService.class);
    private final IOService ioService;
    private final ResourceExtractor extractor;
    private final TestBlockCreator creator;
    private String name;
    List<String> keyAndNumberOfAnswersList;

    public UITestStudentService(IOService ioService, ResourceExtractor extractor, TestBlockCreator creator) {
        this.ioService = ioService;
        this.extractor = extractor;
        this.creator = creator;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption String username) {
        return String.format("Hello %s. For begin test please input command start", username);
    }

    @ShellMethod(value = "Start testing", key = {"start"})
    public String start() {
        InputStream dataFromResourceFile = extractor.getDataFromResourceFile();
        this.keyAndNumberOfAnswersList = extractor.getKeyFromResource(dataFromResourceFile);
        return "Let's start. Enter the name command and put your name";
    }

    @ShellMethod(value = "Ask for name", key = {"name"})
    @ShellMethodAvailability(value = "isKeyAndNumberOfAnswersListNotNull")
    public String name() {
        this.name = ioService.askForName();
        return String.format("Thanks %s. Enter the lastName command and put your lastName", name);
    }

    @ShellMethod(value = "Ask for lastName", key = {"lastName"})
    @ShellMethodAvailability(value = "isKeyAndNumberOfAnswersListNotNull")
    public void lastName() {
        String lastName = ioService.askForLastName();
        Student student = new Student(name, lastName);
        log.warn("{} {} answer the questions.", name, lastName);
        startTesting(student);
    }

    @ShellMethod(value = "Stop testing", key = {"stop"})
    public void stop() {
        log.warn("Test ended");
        System.exit(0);
    }


    private void startTesting(Student student) {
        List<TestBlock> testBlock = creator.createTestBlock(keyAndNumberOfAnswersList, student);
        ioService.printQuestionAnswers(testBlock);
    }

    private Availability isKeyAndNumberOfAnswersListNotNull() {
        return this.keyAndNumberOfAnswersList != null ? Availability.available() : Availability.unavailable("For start testing first command have to 'start'");
    }
}
