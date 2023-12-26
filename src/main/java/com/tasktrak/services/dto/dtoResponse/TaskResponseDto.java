package com.tasktrak.services.dto.dtoResponse;

import lombok.Data;

import java.time.LocalDate;


@Data
public class TaskResponseDto{
    private String title;
    private  String description;
    private  LocalDate creationDate;
    private  LocalDate dueDate;

//    UserDto createdByUser;
}
