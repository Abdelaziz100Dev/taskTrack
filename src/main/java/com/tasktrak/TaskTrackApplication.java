package com.tasktrak;

import com.tasktrak.entities.Task;
import com.tasktrak.enums.TaskStatus;
import com.tasktrak.repositories.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
@EnableScheduling
public class TaskTrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackApplication.class, args);
    }

    //seed the database
//    @Bean
//    CommandLineRunner runner(TaskRepository taskRepository) {
//        return args -> {
//
//            taskRepository.save(new Task(1L, "Create Spring Boot Application", "Create Spring Boot Application", LocalDate.now(), LocalDate.now(), LocalDate.now(), false, TaskStatus.DOING, new HashSet<>(Arrays.asList("Spring", "Boot", "Application")), null, null, null));
//        };
//    }

}
