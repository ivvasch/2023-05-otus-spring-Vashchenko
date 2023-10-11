package ru.otus.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.model.mongomodel.MongoAuthor;
import ru.otus.model.mongomodel.MongoBook;
import ru.otus.model.mongomodel.MongoGenre;
import ru.otus.service.TransferService;

@Slf4j
@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 3;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager manager;

    public JobConfig(JobRepository jobRepository, PlatformTransactionManager manager/*, JpaQueryProvider provider*/) {
        this.jobRepository = jobRepository;
        this.manager = manager;
    }

    @Bean
    public JpaCursorItemReader<Book> readerMongoBook(EntityManagerFactory factory) {
        return new JpaCursorItemReaderBuilder<Book>()
                .name("jpaBookItemReader")
                .entityManagerFactory(factory)
                .queryString("select b from ru.otus.model.Book b")
                .saveState(true)
                .build();
    }
    @Bean
    public JpaCursorItemReader<Author> readerAuthor(EntityManagerFactory factory) {
        return new JpaCursorItemReaderBuilder<Author>()
                .name("jpaAuthorItemReader")
                .entityManagerFactory(factory)
                .saveState(true)
                .queryString("select a from ru.otus.model.Author a")
                .build();
    }
    @Bean
    public JpaCursorItemReader<Genre> readerGenre(EntityManagerFactory factory) {
        return new JpaCursorItemReaderBuilder<Genre>()
                .name("jpaGenreItemReader")
                .entityManagerFactory(factory)
                .saveState(true)
                .queryString("select g from ru.otus.model.Genre g")
                .build();
    }

    @Bean
    public ItemProcessor<Book, MongoBook> processorBook(TransferService transferService) {
        return transferService::transfer;
    }
    @Bean
    @StepScope
    public ItemProcessor<Author, MongoAuthor> processorAuthor(TransferService transferService) {
        return transferService::transferAuthor;
    }
    @Bean
    public ItemProcessor<Genre, MongoGenre> processorGenre(TransferService transferService) {
        return transferService::transferGenre;
    }

    @Bean
    public MongoItemWriter<MongoAuthor> writerAuthor(MongoTemplate template) {
        return new MongoItemWriterBuilder<MongoAuthor>()
                .template(template)
                .collection("t_author")
                .build();
    }
    @Bean
    public MongoItemWriter<MongoGenre> writerGenre(MongoTemplate template) {
        return new MongoItemWriterBuilder<MongoGenre>()
                .template(template)
                .collection("t_genre")
                .build();
    }
    @Bean
    public MongoItemWriter<MongoBook> writerBook(MongoTemplate template) {
        return new MongoItemWriterBuilder<MongoBook>()
                .template(template)
                .collection("t_book")
                .build();
    }

    @Bean
    public Job transferMongoJob(Step mongoAuthorStep, Step mongoGenreStep, Step mongoBookStep) {
        return new JobBuilder("transferJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(mongoAuthorStep)
                .next(mongoGenreStep)
                .next(mongoBookStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info("Start job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info("End job");
                    }
                }).build();
    }

    @Bean
    public Step mongoAuthorStep(JpaCursorItemReader<Author> readerAuthor,
                                MongoItemWriter<MongoAuthor> writerAuthor,
                                ItemProcessor<Author, MongoAuthor> processorAuthor) {
        return new StepBuilder("transformAuthor", jobRepository)
                .<Author, MongoAuthor>chunk(CHUNK_SIZE, manager)
                .reader(readerAuthor)
                .processor(processorAuthor)
                .writer(writerAuthor)
                .listener(new ItemReadListener<Author>() {
                    @Override
                    public void beforeRead() {
                        log.info("Start read from JPA");
                    }

                    @Override
                    public void afterRead(Author item) {
                        log.info("End read from JPA. Author is {}", item.getName());
                    }

                    @Override
                    public void onReadError(Exception ex) {
                        log.info("Some error occurred when we try to read from JPA");
                    }
                }).build();
    }
    @Bean
    public Step mongoGenreStep(JpaCursorItemReader<Genre> readerGenre,
                                MongoItemWriter<MongoGenre> writerGenre,
                                ItemProcessor<Genre, MongoGenre> processorGenre) {
        return new StepBuilder("transformGenre", jobRepository)
                .<Genre, MongoGenre>chunk(CHUNK_SIZE, manager)
                .reader(readerGenre)
                .processor(processorGenre)
                .writer(writerGenre)
                .listener(new ItemReadListener<Genre>() {
                    @Override
                    public void beforeRead() {
                        log.info("Start read from JPA");
                    }

                    @Override
                    public void afterRead(Genre item) {
                        log.info("End read from JPA. Genre is {}", item.getName());
                    }

                    @Override
                    public void onReadError(Exception ex) {
                        log.info("Some error occurred when we try to read from JPA");
                    }
                }).build();
    }
    @Bean
    public Step mongoBookStep(JpaCursorItemReader<Book> readerBook,
                                MongoItemWriter<MongoBook> writerBook,
                                ItemProcessor<Book, MongoBook> processorBook) {
        return new StepBuilder("transformAuthor", jobRepository)
                .<Book, MongoBook>chunk(CHUNK_SIZE, manager)
                .reader(readerBook)
                .processor(processorBook)
                .writer(writerBook)
                .listener(new ItemReadListener<Book>() {
                    @Override
                    public void beforeRead() {
                        log.info("Start read from JPA");
                    }

                    @Override
                    public void afterRead(Book item) {
                        log.info("End read from JPA. Author is {}", item.getTitle());
                    }

                    @Override
                    public void onReadError(Exception ex) {
                        log.info("Some error occurred when we try to read from JPA");
                    }
                }).build();
    }

}
