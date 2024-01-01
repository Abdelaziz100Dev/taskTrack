package com.tasktrak.web;

import com.tasktrak.services.dto.dtoRequest.TaskModiReqRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskModiReqResponseDto;
import com.tasktrak.services.interfaces.ITaskModiReqService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;

@RestController
@RequestMapping("/api/tasks")
public class TaskModificationRequestController {

    private final ITaskModiReqService taskModiReqService;
    public TaskModificationRequestController(ITaskModiReqService taskModiReqService) {
        this.taskModiReqService = taskModiReqService;
    }
    @PostMapping("/modificationRequest")
    public ResponseEntity<TaskModiReqResponseDto> modificationRequest(@RequestBody TaskModiReqRequestDto taskModiReqRequestDto) throws ServerException {
        TaskModiReqResponseDto taskModiReqResponseDto = taskModiReqService.createModificationRequest(taskModiReqRequestDto);

        return new ResponseEntity<>(taskModiReqResponseDto, HttpStatus.OK);
    }

    @PostMapping("/acceptModificationRequest/{id}")
    public ResponseEntity<String> acceptModificationRequest(@PathVariable  Long id) throws ServerException {
        return   taskModiReqService.acceptModificationRequest(id);

//        return new ResponseEntity<>(stringResponse, HttpStatus.OK);
    }
}
