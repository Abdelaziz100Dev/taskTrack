package com.tasktrak.services;

import com.tasktrak.entities.Task;
import com.tasktrak.entities.User;
import com.tasktrak.enums.TaskStatus;
import com.tasktrak.repositories.TaskModificationRequestRepository;
import com.tasktrak.repositories.TaskRepository;
import com.tasktrak.repositories.UserRepository;
import com.tasktrak.services.dto.dtoRequest.TaskRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskResponseDto;
import com.tasktrak.services.dto.dtoResponse.UserAndTasksDto;
import com.tasktrak.services.dto.dtoResponse.UserDto;
import com.tasktrak.services.interfaces.ITaskService;
import com.tasktrak.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements ITaskService {
    TaskModificationRequestRepository taskModificationRequestRepository;

    private TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper, UserRepository userRepository, TaskModificationRequestRepository taskModificationRequestRepository, UserService userService) {

        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.taskModificationRequestRepository = taskModificationRequestRepository;
        this.userService = userService;
    }

    @Override
    public TaskResponseDto addTask(TaskRequestDto taskRequestDto) {
        validation(taskRequestDto);
        Task taskEntity = modelMapper.map(taskRequestDto, Task.class);
        return modelMapper.map(taskRepository.save(taskEntity), TaskResponseDto.class);
    }

    private void validation(TaskRequestDto taskRequestDto) {
        if (taskRequestDto.getCreationDate().isBefore(taskRequestDto.getDueDate()))
            throw new IllegalArgumentException("Due date must be after creation date");
        if (taskRequestDto.getCreationDate().isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Creation date should not be in the past");
        if (taskRequestDto.getTags().size() < 2) throw new IllegalArgumentException("At least tow tags is required");
        if (schedulingDateIsGraterThenThreeDays(taskRequestDto.getStartDate()))
            throw new IllegalArgumentException("StartDate should be less then 3 days from today");
    }

    @Override
    public String markTaskAsDone(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (task.getDueDate().isBefore(LocalDate.now())) throw new IllegalArgumentException("Task is overdue");
        task.setStatus(TaskStatus.DONE);
        taskRepository.save(task);
        return "Task marked as done";
    }

    @Override
    public ResponseEntity<String> deleteTask(Long id) {
        verifyIfTaskCanBeDeleted(id);
        Task task = taskRepository.findById(id).get();
        User AssignedToUser = task.getAssignedToUser();

        try {
            if (!task.getAssignedByUser().isManager()) taskRepository.deleteById(id);
            if (task.getAssignedByUser().isManager()) {
                taskRepository.deleteById(id);
                AssignedToUser.decrementTokensForTaskDeletion();
                AssignedToUser.updateDeletionRequestDate();
                userRepository.save(AssignedToUser);
            }

            return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during task deletion");
        }
    }

    private void verifyIfTaskCanBeDeleted(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!task.getAssignedToUser().canDeleteTask())
            throw new IllegalArgumentException("you can delete one task per month");
//        if(task.isReplaced())throw new IllegalArgumentException("Task is not allowed to delete ");

    }

    private boolean schedulingDateIsGraterThenThreeDays(LocalDate startDate) {
        return startDate.minusDays(3).isAfter(LocalDate.now()) || startDate.minusDays(3).isEqual(LocalDate.now());
    }

    @Override
    public List<UserAndTasksDto> getTasksForManager(User user) {
        User user1 = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        if (user1.isManager()) {
            List<UserAndTasksDto> userAndTasksDtoList = new ArrayList<>();
            List<User> employees = user1.getUsers();

            employees.stream().forEach(employee -> {
                List<List<Task>> listOfListTasks = new ArrayList<>();
                List<Task> tasks1 = employee.getHisTasks();
                listOfListTasks.add(tasks1);
                listOfListTasks.stream().forEach(tasks -> {

//              List<Task> tasks = employee.getHisTasks();
                    // Filter tasks by week, month, and year as needed
                    LocalDate now = LocalDate.now();
                    List<Task> tasksThisWeek = filterTasksByDate(tasks, now.minusDays(now.getDayOfWeek().getValue() - 1), now.plusDays(7));
                    List<Task> tasksThisMonth = filterTasksByDate(tasks, now.withDayOfMonth(1), now.plusMonths(1).withDayOfMonth(1).minusDays(1));
                    List<Task> tasksThisYear = filterTasksByDate(tasks, now.withDayOfYear(1), now.plusYears(1).withDayOfYear(1).minusDays(1));

                    double completionPercentageThisWeek = calculateCompletionPercentage(tasksThisWeek);
                    double completionPercentageThisMonth = calculateCompletionPercentage(tasksThisMonth);
                    double completionPercentageThisYear = calculateCompletionPercentage(tasksThisYear);

                    int tokensUsed = calculateTokensUsedByUsed(employee);

                    UserAndTasksDto userAndTasksDto = new UserAndTasksDto();
                    userAndTasksDto.setUser(modelMapper.map(employee, UserDto.class));
                    userAndTasksDto.setCompletionPercentageThisWeek(completionPercentageThisWeek);
                    userAndTasksDto.setCompletionPercentageThisMonth(completionPercentageThisMonth);
                    userAndTasksDto.setCompletionPercentageThisYear(completionPercentageThisYear);
                    userAndTasksDto.setTokensUsed(tokensUsed);

                    userAndTasksDtoList.add(userAndTasksDto);
                });
            });

            return userAndTasksDtoList;
        } else {
            throw new IllegalArgumentException("User is not a manager");
        }
    }

    private List<Task> filterTasksByDate(List<Task> tasks, LocalDate startDate, LocalDate endDate) {
        return tasks.stream().filter(task -> task.getDueDate().isAfter(startDate) && task.getDueDate().isBefore(endDate)).collect(Collectors.toList());
    }

    private double calculateCompletionPercentage(List<Task> tasks) {
        long totalTasks = tasks.size();
        long completedTasks = tasks.stream().filter(task -> task.getStatus().equals(TaskStatus.DONE)).count();

        return totalTasks == 0 ? 0.0 : (double) completedTasks / totalTasks * 100.0;
    }

    private int calculateTokensUsedByUsed(User user) {
        return user.getTokensUsed();
    }
}