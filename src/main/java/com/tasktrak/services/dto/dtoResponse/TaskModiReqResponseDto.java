package com.tasktrak.services.dto.dtoResponse;

import com.tasktrak.entities.Task;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TaskModiReqResponseDto {

    private Task originalTask;
    private Task newTask;
    private boolean managerApproval;
    private LocalDateTime requestDate;
}
