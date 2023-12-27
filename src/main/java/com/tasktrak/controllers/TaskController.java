package com.tasktrak.controllers;

import com.tasktrak.entities.Task;
import com.tasktrak.entities.User;
import com.tasktrak.services.dto.dtoRequest.TaskRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskResponseDto;
import com.tasktrak.services.dto.dtoResponse.UserAndTasksDto;
import com.tasktrak.services.interfaces.ITaskService;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);

//        return new ResponseEntity<>(stringResponse, HttpStatus.OK);
    }
    @PostMapping("/myEmploysTasks")
    public ResponseEntity<List<UserAndTasksDto>> getTasksForManager(@RequestBody User user){
        return new ResponseEntity<>(taskService.getTasksForManager(user), HttpStatus.OK);
    }

}