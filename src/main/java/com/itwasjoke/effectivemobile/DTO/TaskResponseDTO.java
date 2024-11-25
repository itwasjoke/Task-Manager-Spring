package com.itwasjoke.effectivemobile.DTO;

import com.itwasjoke.effectivemobile.customEnum.PriorityTask;
import com.itwasjoke.effectivemobile.customEnum.StatusTask;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Задача")
public record TaskResponseDTO(
        @Schema(description = "ID задачи", example="1")
        Long id,
        @Schema(description = "Заголовок задачи", example="Создать backend-приложение")
        String title,
        @Schema(description = "Описание задачи", example="Использовать Spring и Java 17")
        String description,
        @Schema(description = "Приоритетность задачи")
        PriorityTask priority,
        @Schema(description = "Статус задачи")
        StatusTask status,
        @Schema(description = "Email  автора", example = "aa@bbb.ru")
        String authorEmail,
        @Schema(description = "Email  исполнителя", example = "aa@bbb.ru")
        String executorEmail,
        @Schema(description = "Комментарии к задаче")
        List<CommentResponseDTO> comments
) {
}
