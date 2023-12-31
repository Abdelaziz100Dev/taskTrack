package com.tasktrak.services.interfaces;

import com.tasktrak.services.dto.dtoRequest.TaskModiReqRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskModiReqResponseDto;
import org.springframework.http.ResponseEntity;

import java.rmi.ServerException;

public interface ITaskModiReqService {

    TaskModiReqResponseDto createModificationRequest(TaskModiReqRequestDto taskModiReqRequestDto) throws ServerException;
    ResponseEntity<String> acceptModificationRequest(Long id) throws ServerException;
}
