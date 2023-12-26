package com.tasktrak.services.dto.dtoRequest;

import com.tasktrak.entities.Task;
import com.tasktrak.entities.User;
import lombok.Data;

@Data
public class TaskModiReqRequestDto {
    private Task originalTask;
    private Task newTask;
    private User requestingUser;
}
