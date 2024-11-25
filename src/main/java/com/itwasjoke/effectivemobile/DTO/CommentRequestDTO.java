package com.itwasjoke.effectivemobile.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Создание комментария")
public record CommentRequestDTO(
        @Schema(description = "Содержимое комментария", example="Какую базу данных использовать?")
        @NotNull
        String text,
        @Schema(description = "ID задачи, к которой нужно добавить комментарий", example="1")
        @NotNull
        Long taskId
) {
}
