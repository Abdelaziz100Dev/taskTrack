package com.tasktrak.mappers;

import com.tasktrak.entities.Task;
import com.tasktrak.services.dto.dtoRequest.TaskRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperStruct {
    Task toTaskRequestEntity(TaskRequestDto taskRequestDto);
}
