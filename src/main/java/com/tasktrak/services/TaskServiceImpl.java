package com.tasktrak.services;

import com.tasktrak.entities.Task;
import com.tasktrak.entities.User;
import com.tasktrak.repositories.TaskRepository;
import com.tasktrak.services.dto.dtoRequest.TaskRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskResponseDto;
import com.tasktrak.services.interfaces.ITaskService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class TaskServiceImpl implements ITaskService {

    private TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public TaskResponseDto addTask(TaskRequestDto taskRequestDto) {
        validation(taskRequestDto);
        Task taskEntity = modelMapper.map(taskRequestDto, Task.class);

        return modelMapper.map(taskRepository.save(taskEntity), TaskResponseDto.class) ;
    }

    private void validation(TaskRequestDto taskRequestDto) {
        if (taskRequestDto.getCreationDate().isBefore(taskRequestDto.getDueDate())) throw new IllegalArgumentException("Due date must be after creation date");
        if (taskRequestDto.getCreationDate().isBefore(LocalDate.now())) throw new IllegalArgumentException("Creation date should not be in the past");
        if (taskRequestDto.getTags().size() < 2) throw new IllegalArgumentException("At least one tag is required");
        if (!shedulingDateIsLessthenThreeDays(taskRequestDto.getCreationDate(), taskRequestDto.getDueDate())) throw new IllegalArgumentException("Due date must be at least 3 days after creation date");
    }

    @Override
    public String markTaskAsDone(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
       if (task.getDueDate().isBefore(LocalDate.now())) throw new IllegalArgumentException("Task is overdue");
        task.setCompleted(true);
        taskRepository.save(task);
        return "Task marked as done" ;
    }

    @Override
    public ResponseEntity<String> deleteTask(Long id) {
        verifyIfTaskCanBeDeleted(id);
        Task task = taskRepository.findById(id).get();
        User AssignedToUser = task.getAssignedToUser();

        try {
            if(!task.getAssignedByUser().isManager())taskRepository.deleteById(id);
            if(task.getAssignedByUser().isManager()){
                taskRepository.deleteById(id);
                task.getAssignedToUser().decrementTokensForTaskDeletion();
            }
            AssignedToUser.updateDeletionRequestDate();

            return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
        } catch (EmptyResultDataAccessException e) {
            // Handle the case where the competition with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during task deletion");
        }
    }

    private void verifyIfTaskCanBeDeleted(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
//        if (task.isCompleted()) throw new IllegalArgumentException("Task is already completed");
//        if (task.getDueDate().isBefore(LocalDate.now())) throw new IllegalArgumentException("Task is overdue");

        if(!task.getAssignedToUser().canDeleteTask()) throw new IllegalArgumentException("User is not allowed to delete task");
        if(task.isReplaced())throw new IllegalArgumentException("Task is not allowed to delete ");

    }

    private boolean shedulingDateIsLessthenThreeDays(LocalDate creationDate, LocalDate dueDate) {
        return dueDate.minusDays(3).isBefore(creationDate);
    }
}
