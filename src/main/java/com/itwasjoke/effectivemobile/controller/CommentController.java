package com.itwasjoke.effectivemobile.controller;

import com.itwasjoke.effectivemobile.DTO.CommentRequestDTO;
import com.itwasjoke.effectivemobile.service.CommentService;
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
@RequestMapping(value = "/comment", produces = "application/json; charset=utf-8")
@Tag(name = "Работа с комментариями", description = "Добавление/удаление комментариев")
public class CommentController {

    final private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @Operation(
            summary = "Создание комментария",
            description = "Добавляет комментарий к задаче"
    )
    public void createComment(
            HttpServletRequest request,
            @Valid @RequestBody CommentRequestDTO commentRequestDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ValidationException("Ошибка валидации:", (Throwable) errors);
        }
        var authentication = (Authentication) request.getUserPrincipal();
        var userDetails = (UserDetails) authentication.getPrincipal();
        commentService.createComment(userDetails.getUsername(), commentRequestDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление комментария",
            description = "Удаляет комментарий у задачи"
    )
    public void deleteComment(
            HttpServletRequest request,
            @PathVariable Long id
    ){
        var authentication = (Authentication) request.getUserPrincipal();
        var userDetails = (UserDetails) authentication.getPrincipal();
        commentService.deleteComment(userDetails.getUsername(), id);
    }
}
