package com.tasktrak.services.dto.dtoRequest;

import com.tasktrak.entities.User;
import com.tasktrak.enums.TaskStatus;
import com.tasktrak.services.dto.dtoResponse.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link com.tasktrak.entities.Task}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto{
    private UserDto createdByUser;
    private UserDto assignedByUser;
    private TaskStatus status = TaskStatus.TODO;

    private String title;
    private  String description;

    private  LocalDate creationDate = LocalDate.now();
    private LocalDate startDate;
    private  LocalDate dueDate;

    private  Boolean completed;
    private Set<String> tags;
}
// json for this class:

