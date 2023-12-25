package com.tasktrak.mappers;

import com.tasktrak.entities.Task;
import com.tasktrak.services.dto.dtoRequest.TaskRequestDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@org.springframework.context.annotation.Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }
    public <S, T> T fromTo(S source, Class<T> targetClass) {
        return Objects.isNull(source) ? null : modelMapper().map(source, targetClass);
    }

    public TaskRequestDto convertToDto(Task task) {
        TaskRequestDto taskRequestDto = TaskRequestDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .creationDate(task.getCreationDate())
                .dueDate(task.getDueDate())
                .completed(task.isCompleted())
                .tags(task.getTags())
                .build();
        return taskRequestDto;
    }
//    public Task convertToEntity(TaskRequestDto taskRequestDto) {
//        return modelMapper().map(taskRequestDto, TaskResponceDto.class);
//    }


}
