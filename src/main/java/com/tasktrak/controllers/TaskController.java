package com.tasktrak.controllers;

import com.tasktrak.entities.Task;
import com.tasktrak.services.dto.dtoRequest.TaskRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskResponseDto;
import com.tasktrak.services.interfaces.ITaskService;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private ITaskService taskService;
    private final ModelMapper modelMapper;

    public TaskController(ITaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }
//    @PostMapping("")
//    public Task addTask(@RequestBody TaskRequestDto taskRequestDto){
//      TaskResponseDto taskResponseDto =  taskService.addTask(taskRequestDto);
//      // map to entity
//      return   modelMapper.map(taskRequestDto, Task.class);
//
////      return taskResponseDto;
//
//    }
    @PostMapping("")
    public ResponseEntity<TaskResponseDto> addTask(@RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto taskResponseDto = taskService.addTask(taskRequestDto);

        return new ResponseEntity<>(taskResponseDto, HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<String> markTaskAsDone(@PathVariable Long id) {
        String stringResponse = taskService.markTaskAsDone(id);

        return new ResponseEntity<>(stringResponse, HttpStatus.OK);
    }


}