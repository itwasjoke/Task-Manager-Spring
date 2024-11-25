package com.itwasjoke.effectivemobile.DTO;

import com.itwasjoke.effectivemobile.customEnum.PriorityTask;
import com.itwasjoke.effectivemobile.customEnum.StatusTask;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Создание задачи")
public record TaskRequestDTO(

        @Schema(description = "ID задачи", example="1")
        Long id,
        @NotNull
        @Size(min = 1, max = 50)
        @Schema(description = "Заголовок задачи", example="Создать backend-приложение")
        String title,
        @Size(max = 200)
        @Schema(description = "Описание задачи", example="Использовать Spring и Java 17")
        String description,
        @NotNull
        @Enumerated()
        @Schema(description = "Приоритетность задачи")
        PriorityTask priority,
        @NotNull
        @Enumerated()
        @Schema(description = "Статус задачи")
        StatusTask status,
        @NotNull
        @Email
        @Schema(description = "Email  исполнителя")
        String emailExecutor
) {
}
