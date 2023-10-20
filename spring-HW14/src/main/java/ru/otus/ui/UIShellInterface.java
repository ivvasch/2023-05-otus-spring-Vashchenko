package ru.otus.ui;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.LocalDateTime;

@ShellComponent
public class UIShellInterface {

    private String userName;
    private final Job transferJob;
    private final JobLauncher launcher;


    public UIShellInterface( Job transferJob, JobLauncher launcher) {
        this.transferJob = transferJob;
        this.launcher = launcher;
    }


    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption String username) {
        this.userName = username;
        return String.format("""
                Hello %s. you can transfer your jpa data to mongo:
                transfer data:                       '-t, -transfer'
                """, username);
    }

    @ShellMethod(value = "Transfer data From H2 to mongo", key = {"-t", "-transfer"})
    public String transferData() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        String result = "All data were transform";
        if (isAvailable()) {
            launcher.run(transferJob, new JobParametersBuilder()
                    .addLocalDateTime("time", LocalDateTime.now())
                    .toJobParameters());
        } else {
            result = String.format("Please login first ");
        }
        return result;
    }

    private boolean isAvailable() {
        return islogin().isAvailable();
    }


    private Availability islogin() {
        return userName != null ? Availability.available()
                : Availability.unavailable("For start work with library please 'login'");
    }
}
