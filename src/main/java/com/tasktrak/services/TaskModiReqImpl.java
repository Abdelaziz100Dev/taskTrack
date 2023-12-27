package com.tasktrak.services;
import com.tasktrak.entities.Task;
import com.tasktrak.entities.TaskModificationRequest;
import com.tasktrak.entities.User;
import com.tasktrak.repositories.TaskModificationRequestRepository;
import com.tasktrak.repositories.TaskRepository;
import com.tasktrak.repositories.UserRepository;
import com.tasktrak.services.dto.dtoRequest.TaskModiReqRequestDto;
import com.tasktrak.services.dto.dtoResponse.TaskModiReqResponseDto;
import com.tasktrak.services.interfaces.ITaskModiReqService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;

@Service
public class TaskModiReqImpl implements ITaskModiReqService {
    TaskModificationRequestRepository taskModificationRequestRepository;
    TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    public TaskModiReqImpl(TaskModificationRequestRepository taskModificationRequestRepository,TaskRepository taskRepository, ModelMapper modelMapper,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskModificationRequestRepository = taskModificationRequestRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }
    @Override
    public TaskModiReqResponseDto creatDemend(TaskModiReqRequestDto taskModiReqRequestDto) throws ServerException {
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

    @Override
    public void acceptDemend(TaskModiReqRequestDto taskModiReqRequestDto) throws ServerException {
        TaskModificationRequest taskModificationRequest = taskModificationRequestRepository.findById(taskModiReqRequestDto.getId()).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        Task originalTask = taskModificationRequest.getOriginalTask();
        Task newTask = taskModificationRequest.getNewTask();
        User userHaveTask = originalTask.getAssignedToUser();

        User randomUser = userRepository.getReferenceById(userHaveTask.getId()- 1);

            userHaveTask.getHisTasks().remove(originalTask);
            userHaveTask.getHisTasks().add(newTask);
            newTask.setAssignedToUser(userHaveTask);
            originalTask.setAssignedToUser(randomUser);

            userRepository.save(userHaveTask);
            taskRepository.save(originalTask);
            taskRepository.save(newTask);

            originalTask.setReplaced(true);
            newTask.setReplaced(true);
            taskModificationRequest.setAccepted(true);
            taskModificationRequestRepository.save(taskModificationRequest);
    }
}
