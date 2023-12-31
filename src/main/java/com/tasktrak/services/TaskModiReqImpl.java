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
import org.springframework.transaction.annotation.Transactional;

import java.rmi.ServerException;

@Service
public class TaskModiReqImpl implements ITaskModiReqService {
    TaskModificationRequestRepository taskModificationRequestRepository;
    TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private  UserServiceImp userServiceImp;


    public TaskModiReqImpl(TaskModificationRequestRepository taskModificationRequestRepository,TaskRepository taskRepository, ModelMapper modelMapper,
                           UserRepository userRepository, UserServiceImp userServiceImp) {
        this.taskRepository = taskRepository;
        this.taskModificationRequestRepository = taskModificationRequestRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.userServiceImp = userServiceImp;
    }
    @Override
    @Transactional
    public TaskModiReqResponseDto creatDemend(TaskModiReqRequestDto taskModiReqRequestDto) throws ServerException {
        Task originalTask = taskRepository.findById(taskModiReqRequestDto.getOriginalTask().getId()).get();
        Task newTask = taskRepository.getReferenceById(taskModiReqRequestDto.getNewTask().getId());
        User userWithThisTask = userServiceImp.getUserById(taskModiReqRequestDto.getRequestingUser().id());

        boolean cantMakeRequest = !userWithThisTask.canMakeRequestForModification();
        boolean isReplaced = newTask.isReplaced();
        if (cantMakeRequest) {
            throw new ServerException("->you can do just tow modification request per day");
        }
        if (isReplaced) {
            throw new ServerException("->you can't make modification request, this task is already replaced");
        }

        TaskModificationRequest taskModificationRequest = new TaskModificationRequest();

        taskModificationRequest.setOriginalTask(originalTask);
        taskModificationRequest.setNewTask(newTask);
        taskModificationRequest.setRequestingUser(userWithThisTask);
        taskModificationRequest.setRequestDate(java.time.LocalDateTime.now());

        TaskModificationRequest taskModificationRequest1 = taskModificationRequestRepository.save(taskModificationRequest);

        userWithThisTask.updateModificationRequestDate();
        userWithThisTask.decrementTokensForTaskModification();
        userRepository.save(userWithThisTask);

        return modelMapper.map(taskModificationRequest1, TaskModiReqResponseDto.class);
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
