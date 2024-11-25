package com.itwasjoke.effectivemobile.security;

import com.itwasjoke.effectivemobile.DTO.UserRequestDTO;
import com.itwasjoke.effectivemobile.security.jwt.JwtAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping("/auth")
@Tag(name = "Авторизация", description = "Вход и регистрация")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    @Operation(
            summary = "Регистрация",
            description = "Создание объекта пользователя и возвращение токена авторизации"
    )
    public JwtAuthenticationResponse signUp(
            @Valid @RequestBody UserRequestDTO user,
            BindingResult bindingResult
    ){
        System.out.println(bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ValidationException("Ошибка валидации:", (Throwable) errors);
        }
        return authService.signUp(user);
    }

    @PostMapping("/sign-in")
    @Operation(
            summary = "Вход",
            description = "Отправка логина и пароля, получение токена авторизации"
    )
    public JwtAuthenticationResponse signIn(
            @RequestParam @Parameter(name = "email", description = "Почта") String email,
            @RequestParam @Parameter(name = "password", description = "Пароль") String password
    ) {
        return authService.signIn(email, password);
    }

}
