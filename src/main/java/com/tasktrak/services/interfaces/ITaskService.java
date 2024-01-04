package com.tasktrak.services.interfaces;

import com.tasktrak.entities.User;
import com.tasktrak.services.dto.dtoRequest.TaskRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskResponseDto;
import com.tasktrak.services.dto.dtoResponse.UserAndTasksDto;
import com.tasktrak.services.dto.dtoResponse.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITaskService {
    TaskResponseDto addTask(TaskRequestDto taskDtoRequest);
    String markTaskAsDone(Long id);
    ResponseEntity<String> deleteTask(Long id);

    List<UserAndTasksDto> getTasksForManager(Long managerId);
}
