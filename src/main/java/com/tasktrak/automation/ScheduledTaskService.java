package com.tasktrak.automation;

import com.tasktrak.entities.Task;
import com.tasktrak.repositories.TaskModificationRequestRepository;
import com.tasktrak.repositories.TaskRepository;
import com.tasktrak.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ScheduledTaskService {
    TaskModificationRequestRepository taskModificationRequestRepository;
    private TaskRepository taskRepository;
    private final UserRepository userRepository;

    public ScheduledTaskService(TaskRepository taskRepository, ModelMapper modelMapper,
                           UserRepository userRepository,TaskModificationRequestRepository taskModificationRequestRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskModificationRequestRepository = taskModificationRequestRepository;
    }
    @Scheduled(fixedRate = 20_000) //  43_200_000 Run every 12 hours (12 * 60 * 60 * 1000 milliseconds)
    public void simulateDailyTasks() {
        taskModificationRequestRepository.findAll().stream()
                .filter(r-> Duration.between(r.getRequestDate(), LocalDateTime.now()).toHours() > 2)
                .forEach(r ->{
                    r.getRequestingUser().doubleTheModificationTokensStock();
                    userRepository.save(r.getRequestingUser());
                });
        System.out.println("-------------- Daily task simulation is running");
    }

    @Scheduled(fixedRate = 86400000) // Run every 24 hours (24 * 60 * 60 * 1000 milliseconds)
    public void markOverdueTasksAsNotCompleted() {
        taskRepository.findAll().stream()
                .filter(t-> t.isOverdue())
                .forEach(t->t.setNotComplete());
    }
}
