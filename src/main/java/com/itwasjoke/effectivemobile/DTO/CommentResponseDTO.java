package com.itwasjoke.effectivemobile.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Комментарий")
public record CommentResponseDTO(
        @Schema(description = "ID комментария", example="1")
        Long id,
        @Schema(description = "Содержимое комментария", example="Нужно создать БД на PostgreSQL")
        String text,
        @Schema(description = "Email пользователя, который отправил комментарий", example="aaa@bb.ru")
        String name
) {
}
