package com.tasktrak.controllers;

import com.tasktrak.services.dto.dtoRequest.TaskRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks/modification")
public class TaskModificationRequestController {

//    @PostMapping("")
//    public ResponseEntity<TaskResponseDto> addTask(@RequestBody TaskRequestDto taskRequestDto) {
//        TaskResponseDto taskResponseDto = taskService.addTask(taskRequestDto);
//
//        return new ResponseEntity<>(taskResponseDto, HttpStatus.OK);
//    }
}
