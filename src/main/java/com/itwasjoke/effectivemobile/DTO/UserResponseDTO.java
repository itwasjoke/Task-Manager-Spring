package com.itwasjoke.effectivemobile.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Пользователь")
public record UserResponseDTO(
        @Schema(description = "Имя", example = "Андрей")
        String name,
        @Schema(description = "Фамилия", example = "Васильев")
        String surname,
        @Schema(description = "Системная роль")
        String role,
        @Schema(description = "Почта", example = "aa@bb.ru")
        String email
) {
}
