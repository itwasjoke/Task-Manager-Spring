package com.itwasjoke.effectivemobile.DTO;

import com.itwasjoke.effectivemobile.customEnum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Создание пользователя")
public record UserRequestDTO(
        @NotNull
        @Size(min = 1, max = 50)
        @Schema(description = "Имя", example = "Андрей")
        String name,
        @NotNull
        @Size(min = 1, max = 50)
        @Schema(description = "Фамилия", example = "Васильев")
        String surname,
        @NotNull
        @Email
        @Schema(description = "Почта", example = "aa@bb.ru")
        String email,
        @NotNull
        @Size(min = 1, max = 50)
        @Schema(description = "Пароль ", example = "abc123")
        String password,
        @NotNull
        @Schema(description = "Системная роль")
        @Enumerated()
        Role role
        ) {
}
