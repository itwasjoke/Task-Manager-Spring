package com.itwasjoke.effectivemobile.controller;

import com.itwasjoke.effectivemobile.DTO.TaskRequestDTO;
import com.itwasjoke.effectivemobile.DTO.TaskResponseDTO;
import com.itwasjoke.effectivemobile.customEnum.FilterTask;
import com.itwasjoke.effectivemobile.customEnum.PriorityTask;
import com.itwasjoke.effectivemobile.customEnum.StatusTask;
import com.itwasjoke.effectivemobile.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/task", produces = "application/json; charset=utf-8")
@Tag(name = "Задача", description = "Работа с задачами")
public class TaskController {

    final private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(
            summary = "Создание задачи",
            description = "Добавляет задачу пользователю"
    )
    public Long createTask(
            HttpServletRequest request,
            @Valid @RequestBody TaskRequestDTO taskRequestDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ValidationException("Ошибка валидации:", (Throwable) errors);
        }
        var authentication = (Authentication) request.getUserPrincipal();
        var userDetails = (UserDetails) authentication.getPrincipal();
        return taskService.createTask(userDetails.getUsername(), taskRequestDTO);
    }

    @GetMapping
    @Operation(
            summary = "Получение задач пользователя",
            description = """
    Вводится email пользователя, чьи задачи нужно посмотреть.
    Также указывается, нужно посмотреть задачи автора или исполнителя по Email и другие фильтры.
    """
    )
    public List<TaskResponseDTO> getUserTasks(
            @RequestParam String email,
            @RequestParam FilterTask filter,
            @RequestParam StatusTask status,
            @RequestParam PriorityTask priority,
            @RequestParam int page
    ){
        return taskService.getUserTasks(email, filter, status, priority, page);
    }

    @PutMapping
    @Operation(
            summary = "Изменение задачи",
            description = "Можно изменить статус/приоритетность/название/описание"
    )
    public void changeTask(
            HttpServletRequest request,
            @Valid @RequestBody TaskRequestDTO taskRequestDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ValidationException("Ошибка валидации:", (Throwable) errors);
        }
        var authentication = (Authentication) request.getUserPrincipal();
        var userDetails = (UserDetails) authentication.getPrincipal();
        taskService.changeTask(userDetails.getUsername(), taskRequestDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление задачи",
            description = "Удаляет задачу, если ты автор или исполнитель"
    )
    public void deleteTaskById(
            HttpServletRequest request,
            @PathVariable Long id
    ){
        var authentication = (Authentication) request.getUserPrincipal();
        var userDetails = (UserDetails) authentication.getPrincipal();
        taskService.deleteTaskById(userDetails.getUsername(), id);
    }

}
