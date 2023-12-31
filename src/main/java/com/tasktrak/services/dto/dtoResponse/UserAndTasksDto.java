package com.tasktrak.services.dto.dtoResponse;

import com.tasktrak.entities.Task;
import com.tasktrak.entities.User;
import lombok.Data;

import java.util.List;
@Data
public class UserAndTasksDto {
    private UserDto user;
    private double completionPercentageThisWeek;
    private double completionPercentageThisMonth;
    private  double completionPercentageThisYear;
    private int tokensUsed;
    private List<Task> tasks;

}
