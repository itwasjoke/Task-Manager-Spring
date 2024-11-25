package com.itwasjoke.effectivemobile.DTO.mapper;

import com.itwasjoke.effectivemobile.DTO.CommentResponseDTO;
import com.itwasjoke.effectivemobile.entity.Comment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = CommentMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CommentListMapper {
    List<CommentResponseDTO> map(List<Comment> comments);
}
