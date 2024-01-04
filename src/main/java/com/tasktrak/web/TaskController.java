package com.tasktrak.web;

import com.tasktrak.entities.User;
import com.tasktrak.services.dto.dtoRequest.TaskRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskResponseDto;
import com.tasktrak.services.dto.dtoResponse.UserAndTasksDto;
import com.tasktrak.services.dto.dtoResponse.UserDto;
import com.tasktrak.services.interfaces.ITaskService;
import jakarta.websocket.server.PathParam;
import org.modelmapper.ModelMapper;
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
    @PostMapping("")
    public ResponseEntity<TaskResponseDto> addTask(@RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto taskResponseDto = taskService.addTask(taskRequestDto);

        return new ResponseEntity<>(taskResponseDto, HttpStatus.OK);
    }

    @PostMapping("markTaskAsDone/{id}")
    public ResponseEntity<String> markTaskAsDone(@PathVariable Long id) {
        String stringResponse = taskService.markTaskAsDone(id);

        return new ResponseEntity<>(stringResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }
    @GetMapping("/myEmploysTasks/{id}")
    public ResponseEntity<List<UserAndTasksDto>> getTasksForManager(@PathVariable Long id){
        return new ResponseEntity<>(taskService.getTasksForManager(id), HttpStatus.OK);
//        return null;
    }

}