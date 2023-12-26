package com.tasktrak.controllers;

import com.tasktrak.services.dto.dtoRequest.TaskModiReqRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskModiReqResponseDto;
import com.tasktrak.services.dto.dtoResponse.TaskResponseDto;
import com.tasktrak.services.interfaces.ITaskModiReqService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks/modification")
public class TaskModificationRequestController {

    private final ITaskModiReqService taskModiReqService;
    public TaskModificationRequestController(ITaskModiReqService TaskModiReqService) {
        this.taskModiReqService = TaskModiReqService;
    }
    @PostMapping("")
    public ResponseEntity<TaskModiReqResponseDto> addTask(@RequestBody TaskModiReqRequestDto taskModiReqRequestDto) {
        TaskModiReqResponseDto taskModiReqResponseDto = taskModiReqService.creatDemend(taskModiReqRequestDto);

        return new ResponseEntity<>(taskModiReqResponseDto, HttpStatus.OK);
    }
}
