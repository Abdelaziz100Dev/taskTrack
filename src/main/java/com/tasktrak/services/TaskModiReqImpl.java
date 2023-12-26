package com.tasktrak.services;
import com.tasktrak.entities.Task;
import com.tasktrak.entities.TaskModificationRequest;
import com.tasktrak.entities.User;
import com.tasktrak.repositories.TaskModificationRequestRepository;
import com.tasktrak.repositories.TaskRepository;
import com.tasktrak.services.dto.dtoRequest.TaskModiReqRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskModiReqResponseDto;
import com.tasktrak.services.dto.dtoResponse.TaskResponseDto;
import com.tasktrak.services.interfaces.ITaskModiReqService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;

@Service
public class TaskModiReqImpl implements ITaskModiReqService {
    TaskModificationRequestRepository taskModificationRequestRepository;
    TaskRepository taskRepository;
    private final ModelMapper modelMapper;


    public TaskModiReqImpl(TaskModificationRequestRepository taskModificationRequestRepository,TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.taskModificationRequestRepository = taskModificationRequestRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public TaskModiReqResponseDto creatDemend(TaskModiReqRequestDto taskModiReqRequestDto) throws ServerException {

//        Task task =   taskModiReqRequestDto.getOriginalTask();
//        Task originalTask = taskRepository.getById(task.getId());
//        Task newTask = taskRepository.getById(taskModiReqRequestDto.getNewTask().getId());
//        User AssignedToUser = originalTask.getAssignedToUser();
//        User userAssignedThisTask = originalTask.getAssignedToUser();
//
//
//        AssignedToUser.decrementTokensForTaskModification();
//
//        boolean b = AssignedToUser.canMakeModification();
//        boolean b1 = userAssignedThisTask.isManager();
//
//        if (b && b1) {
//            AssignedToUser.getTasksAssigned().add(newTask);
//            AssignedToUser.getTasksAssigned().remove(originalTask);
//            AssignedToUser.updateModificationDate();
//
//            taskModificationRequestRepository.save(taskModiReqRequestDto);
//        }
//        return modelMapper.map(task, TaskResponseDto.class);
        Task task =   taskModiReqRequestDto.getOriginalTask();
        Task originalTask = taskRepository.getReferenceById(task.getId());
        Task newTask = taskRepository.getReferenceById(taskModiReqRequestDto.getNewTask().getId());
        User AssignedToUser = originalTask.getAssignedToUser();
        User userAssignedThisTask = originalTask.getAssignedToUser();

        AssignedToUser.decrementTokensForTaskModification();

        boolean b = AssignedToUser.canMakeRequestForModification();
        boolean b1 = userAssignedThisTask.isManager();
        boolean b2 = !newTask.isReplaced();

        if (b && b1 && b2) {
        TaskModificationRequest taskModificationRequest = new TaskModificationRequest();

            taskModificationRequest.setOriginalTask(originalTask);
            taskModificationRequest.setNewTask(newTask);
            taskModificationRequest.setRequestingUser(AssignedToUser);
            taskModificationRequest.setRequestDate(java.time.LocalDateTime.now());

            TaskModificationRequest taskModificationRequest1 = taskModificationRequestRepository.save(taskModificationRequest);
            AssignedToUser.updateModificationRequestDate();

        return modelMapper.map(taskModificationRequest1, TaskModiReqResponseDto.class);
        }else {
            throw new ServerException("wrong with modification request");
        }
    }

}
