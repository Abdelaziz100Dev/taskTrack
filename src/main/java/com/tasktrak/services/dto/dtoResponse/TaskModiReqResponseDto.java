package com.tasktrak.services.dto.dtoResponse;

import com.tasktrak.entities.Task;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TaskModiReqResponseDto {

    private TaskResponseDto originalTask;
    private TaskResponseDto newTask;
    private boolean managerApproval;
    private LocalDateTime requestDate;
}
