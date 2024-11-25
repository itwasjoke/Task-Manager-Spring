package com.itwasjoke.effectivemobile.exception;

import com.itwasjoke.effectivemobile.exception.comment.CommentNotFoundException;
import com.itwasjoke.effectivemobile.exception.task.TaskNoAccessException;
import com.itwasjoke.effectivemobile.exception.task.TaskNotFoundException;
import com.itwasjoke.effectivemobile.exception.user.UserAlreadyExistsException;
import com.itwasjoke.effectivemobile.exception.user.UserNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            UserNotFoundException.class,
            TaskNoAccessException.class,
            TaskNoAccessException.class,
            CommentNotFoundException.class,
            ValidationException.class
    })
    public ResponseEntity<Object> handleAllException(Exception e, WebRequest request) {
        String responseBody;
        HttpStatus status;

        if (e instanceof UserAlreadyExistsException) {
            responseBody = "Такой пользователь уже существует";
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof UserNotFoundException) {
            responseBody = "Пользователь не найден";
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof TaskNoAccessException){
            responseBody = "Нет доступа к этой задаче";
            status = HttpStatus.FORBIDDEN;
        } else if (e instanceof TaskNotFoundException){
            responseBody = "Задача не найдена";
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof  CommentNotFoundException) {
            responseBody = "Комментарий не найден";
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof ValidationException) {
            responseBody = "Ошибка валидации: " + e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        } else {
            responseBody = "Неизвестная ошибка";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return handleExceptionInternal(e, responseBody, new HttpHeaders(), status, request);
    }
}