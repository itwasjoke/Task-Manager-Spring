package com.itwasjoke.effectivemobile.service.impl;

import com.itwasjoke.effectivemobile.DTO.TaskRequestDTO;
import com.itwasjoke.effectivemobile.DTO.TaskResponseDTO;
import com.itwasjoke.effectivemobile.DTO.mapper.TaskListMapper;
import com.itwasjoke.effectivemobile.DTO.mapper.TaskMapper;
import com.itwasjoke.effectivemobile.customEnum.FilterTask;
import com.itwasjoke.effectivemobile.customEnum.PriorityTask;
import com.itwasjoke.effectivemobile.customEnum.StatusTask;
import com.itwasjoke.effectivemobile.entity.Task;
import com.itwasjoke.effectivemobile.entity.User;
import com.itwasjoke.effectivemobile.exception.task.TaskNoAccessException;
import com.itwasjoke.effectivemobile.exception.task.TaskNotFoundException;
import com.itwasjoke.effectivemobile.repository.TaskRepository;
import com.itwasjoke.effectivemobile.service.TaskService;
import com.itwasjoke.effectivemobile.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskListMapper taskListMapper;
    private final UserService userService;

    public TaskServiceImpl(
            TaskRepository taskRepository,
            TaskMapper taskMapper,
            TaskListMapper taskListMapper,
            UserService userService
    ) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskListMapper = taskListMapper;
        this.userService = userService;
    }

    @Override
    public Long createTask(String email, TaskRequestDTO taskRequestDTO) {
        Task task = taskMapper.requestToTask(taskRequestDTO);
        User author = userService.getUser(email);
        User executor = userService.getUser(taskRequestDTO.emailExecutor());
        task.setAuthor(author);
        task.setExecutor(executor);
        task.setId(null);
        Task taskCreated = taskRepository.save(task);
        return taskCreated.getId();
    }

    @Override
    public Task getTaskById(Long id) {
        Optional<Task> task = taskRepository.findTaskById(id);
        if (task.isPresent()){
            return task.get();
        } else {
            throw new TaskNotFoundException("Task not found in getTaskById() func");
        }

    }

    @Override
    public List<TaskResponseDTO> getUserTasks(
            String email,
            FilterTask filter,
            StatusTask status,
            PriorityTask priority,
            int page
    ) {
        User user = userService.getUser(email);
        Pageable paging = PageRequest.of(page, 5);
        switch (filter){
            case AUTHOR -> {
                Page<Task> tasksList = taskRepository.findTasksByAuthorAndStatusAndPriority(
                        user,
                        status,
                        priority,
                        paging
                );
                return taskListMapper.map(tasksList.toList());
            }
            case EXECUTOR -> {
                Page<Task> tasksList = taskRepository.findTasksByExecutorAndStatusAndPriority(
                        user,
                        status,
                        priority,
                        paging
                );
                return taskListMapper.map(tasksList.toList());
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public boolean taskIsAvailable(Task task, User user){
        return task.getAuthor().getId().equals(user.getId())
                || task.getExecutor().getId().equals(user.getId());
    }

    @Override
    public void changeTask(String email, TaskRequestDTO taskRequestDTO) {
        User user = userService.getUser(email);
        Task task = getTaskById(taskRequestDTO.id());
        if (taskIsAvailable(task, user)){
            task.setDescription(taskRequestDTO.description());
            task.setTitle(taskRequestDTO.title());
            task.setPriority(taskRequestDTO.priority());
            task.setStatus(taskRequestDTO.status());
            taskRepository.save(task);
        } else {
            throw new TaskNoAccessException("For task with ID " + taskRequestDTO.id() + " no access");
        }
    }

    @Override
    public void deleteTaskById(String email, Long id) {
        User user = userService.getUser(email);
        Task task = getTaskById(id);
        if (taskIsAvailable(task, user)){
            taskRepository.delete(task);
        } else {
            throw new TaskNoAccessException("For task with ID " + id + " no access");
        }
    }
}
