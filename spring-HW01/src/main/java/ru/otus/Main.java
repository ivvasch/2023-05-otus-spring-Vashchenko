package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.model.TestBlock;
import ru.otus.service.PrintTest;
import ru.otus.service.ResourceExtractor;

import java.io.InputStream;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        ResourceExtractor extractor = context.getBean(ResourceExtractor.class);

        InputStream dataFromResourceFile = extractor.getDataFromResourceFile();
        List<TestBlock> testBlocks = extractor.createTestBlock(dataFromResourceFile);
        PrintTest print = new PrintTest();
        print.printResult(testBlocks);
    }
}
