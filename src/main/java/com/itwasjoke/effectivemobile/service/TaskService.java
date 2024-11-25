package com.itwasjoke.effectivemobile.service;

import com.itwasjoke.effectivemobile.DTO.TaskRequestDTO;
import com.itwasjoke.effectivemobile.DTO.TaskResponseDTO;
import com.itwasjoke.effectivemobile.customEnum.FilterTask;
import com.itwasjoke.effectivemobile.customEnum.PriorityTask;
import com.itwasjoke.effectivemobile.customEnum.StatusTask;
import com.itwasjoke.effectivemobile.entity.Task;
import com.itwasjoke.effectivemobile.entity.User;

import java.util.List;

public interface TaskService {

    Long createTask(
        String email,
        TaskRequestDTO taskRequestDTO
    );
    Task getTaskById(Long id);

    boolean taskIsAvailable(Task task, User user);

    List<TaskResponseDTO> getUserTasks(
            String email,
            FilterTask filter,
            StatusTask status,
            PriorityTask priority,
            int page
    );
    void changeTask(
            String email,
            TaskRequestDTO taskRequestDTO
    );
    void deleteTaskById(
            String email,
            Long id
    );
}
