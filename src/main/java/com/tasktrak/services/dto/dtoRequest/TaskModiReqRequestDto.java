package com.tasktrak.services.dto.dtoRequest;

import com.tasktrak.entities.Task;
import com.tasktrak.entities.User;
import com.tasktrak.services.dto.TaskDto;
import com.tasktrak.services.dto.dtoResponse.UserDto;
import lombok.Data;

@Data
public class TaskModiReqRequestDto {
    private Long id;
    private TaskDto originalTask;
    private TaskDto newTask;
    private UserDto requestingUser;
}
//Json for this DTO:
// {
//         "id": 1,
//         "originalTask": {
//         "id": 1,
//         "title": "task1",
//         },
//         "newTask": {
//         "id": 2,
//         "title": "task2",
//         },
//         "requestingUser": {
//         "id": 1,
//         "firstName": "user1",
//}
// }

