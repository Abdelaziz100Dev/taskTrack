package com.tasktrak;

import com.tasktrak.entities.Task;
import com.tasktrak.entities.User;
import com.tasktrak.enums.TaskStatus;
import com.tasktrak.repositories.TaskRepository;
import com.tasktrak.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private TaskRepository taskRepository;
    public static void main(String[] args) {
        SpringApplication.run(TaskTrackApplication.class, args);
    }

    //seed the database
//    @Bean
//    CommandLineRunner runner(TaskRepository taskRepository,
//                             UserRepository userRepository) {
//        return args -> {
//            // create INSERT INTO `tasktrack`.`user` (`id`, `is_manager`, `last_deletion_date`, `last_modification_requset_date`, `tokens_for_task_deletion`, `tokens_for_task_modification`, `tokens_used`, `username`) VALUES ('5', b'1', '2023-12-30', '2023-12-31', '1', '2', '0', 'manger');
//            User user  =   User.builder().id(1L).username("manager").isManager(true).lastDeletionDate(LocalDate.parse("2023-12-30")).lastModificationRequsetDate(LocalDate.parse("2023-12-31")).tokensForTaskDeletion(1).tokensForTaskModification(2).tokensUsed(0).build();
//            User user1 =  User.builder().id(2L).username("rached").isManager(false).lastDeletionDate(LocalDate.parse("2023-12-30")).lastModificationRequsetDate(LocalDate.parse("2023-12-31")).tokensForTaskDeletion(1).tokensForTaskModification(2).tokensUsed(0).build();
//            User user2 =   User.builder().id(3L).username("abdelaziz").isManager(false).lastDeletionDate(LocalDate.parse("2023-12-30")).lastModificationRequsetDate(LocalDate.parse("2023-12-31")).tokensForTaskDeletion(1).tokensForTaskModification(2).tokensUsed(0).build();
//            userRepository.saveAll(Arrays.asList(user, user1, user2));
//        };
//    }

}
