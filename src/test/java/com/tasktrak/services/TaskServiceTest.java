package com.tasktrak.services;

import com.tasktrak.entities.Task;
import com.tasktrak.mappers.MapperStruct;
import com.tasktrak.repositories.TaskModificationRequestRepository;
import com.tasktrak.repositories.TaskRepository;
import com.tasktrak.repositories.UserRepository;
import com.tasktrak.services.dto.dtoRequest.TaskRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskServiceImpl taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskModificationRequestRepository taskModificationRequestRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private MapperStruct mapperStruct;
    @Test
    public void test_add_task_with_valid_input() {
        // Create instance of TaskServiceImpl with mocked dependencies
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository, modelMapper, userRepository, taskModificationRequestRepository, mapperStruct);

        // Create a valid task request DTO
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setStartDate(LocalDate.now().plusDays(1));
        taskRequestDto.setDueDate(LocalDate.now().plusDays(7));
        taskRequestDto.setTags(new HashSet<>(Arrays.asList("tag1", "tag2")));

        TaskResponseDto taskResponseDto = new TaskResponseDto();


        // Mock the mapperStruct.toTaskRequestEntity() method to return a task entity
        Task taskEntity = new Task();
        Mockito.when(mapperStruct.toTaskRequestEntity(taskRequestDto)).thenReturn(taskEntity);


        // Mock the taskRepository.save() method to return the saved task entity
        Mockito.when(taskRepository.save(taskEntity)).thenReturn(taskEntity);
        Mockito.when(modelMapper.map(taskEntity,TaskResponseDto.class)).thenReturn(taskResponseDto);



        // Call the addTask method and assert the result
        TaskResponseDto result = taskService.addTask(taskRequestDto);
        assertEquals(taskResponseDto, result);
    }

    @Test
    public void test_add_task_with_start_date_after_due_date() {
        // Create instance of TaskServiceImpl with mocked dependencies
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository, modelMapper, userRepository, taskModificationRequestRepository, mapperStruct);

        // Create a valid task request DTO
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setStartDate(LocalDate.now().plusDays(7));
        taskRequestDto.setDueDate(LocalDate.now().plusDays(1));
        taskRequestDto.setTags(new HashSet<>(Arrays.asList("tag1", "tag2")));

        assertThrows(IllegalArgumentException.class, () -> {
            taskService.addTask(taskRequestDto);
        });
    }

    @Test
    public void test_add_task_with_start_date_should_not_be_in_the_past() {
        // Create instance of TaskServiceImpl with mocked dependencies
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository, modelMapper, userRepository, taskModificationRequestRepository, mapperStruct);

        // Create a valid task request DTO
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setStartDate(LocalDate.now().minusDays(7));
        taskRequestDto.setDueDate(LocalDate.now().plusDays(1));
        taskRequestDto.setTags(new HashSet<>(Arrays.asList("tag1", "tag2")));

        assertThrows(IllegalArgumentException.class, () -> {
            taskService.addTask(taskRequestDto);
        });
    }
}