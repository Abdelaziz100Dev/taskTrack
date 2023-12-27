package com.tasktrak.services.dto.dtoResponse;

import com.tasktrak.entities.Task;
import com.tasktrak.entities.User;
import lombok.Data;

import java.util.List;
@Data
public class UserAndTasksDto {
    private User user;
    private List<Task> tasks;
}
