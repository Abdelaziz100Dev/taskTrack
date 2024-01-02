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
// {
//         "createdByUser": {
    //         "id": 1,
    //         "firstName": "string",
    //         "lastName": "string",
    //         "email": "string",
    //         "password": "string",
    //         "role": "MANAGER",
    //         "manager": true,
    //         "tasks": [
    //         null
    //         ],
    //         "assignedTasks": [
    //         null
    //         ],
    //         "assignedByTasks": [
    //         null
    //         ],
    //         "createdTasks": [
    //         null
    //         ],
    //         "taskModificationRequests": [
    //         null
    //         ]
//         },
    //         "title": "string",
    //         "description": "string",
    //         "creationDate": "2021-08-09",
    //         "dueDate": "2021-08-09",
    //         "completed": true,
    //         "tags": [
    //         "string"
    //         ]
//         }
