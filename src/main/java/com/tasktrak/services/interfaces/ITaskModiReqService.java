package com.tasktrak.services.interfaces;

import com.tasktrak.services.dto.dtoRequest.TaskModiReqRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskModiReqResponseDto;
import com.tasktrak.services.dto.dtoResponse.TaskResponseDto;

import java.rmi.ServerException;

public interface ITaskModiReqService {

    TaskModiReqResponseDto creatDemend(TaskModiReqRequestDto taskModiReqRequestDto) throws ServerException;

    void acceptDemend(TaskModiReqRequestDto taskModiReqRequestDto) throws ServerException;
}
