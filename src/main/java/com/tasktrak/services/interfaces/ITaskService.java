package com.tasktrak.services.interfaces;

import com.tasktrak.services.dto.dtoRequest.TaskRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskResponseDto;
import org.springframework.http.ResponseEntity;

public interface ITaskService {
    TaskResponseDto addTask(TaskRequestDto taskDtoRequest);

    String markTaskAsDone(Long id);
}
