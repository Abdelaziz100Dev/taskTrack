package com.tasktrak.services.dto;

import com.tasktrak.entities.User;
import com.tasktrak.services.dto.dtoResponse.UserDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
    private Long id;
    private UserDto assignedToUser;
}
