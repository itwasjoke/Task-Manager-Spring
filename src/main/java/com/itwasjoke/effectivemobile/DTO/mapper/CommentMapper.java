package com.itwasjoke.effectivemobile.DTO.mapper;


import com.itwasjoke.effectivemobile.DTO.CommentRequestDTO;
import com.itwasjoke.effectivemobile.DTO.CommentResponseDTO;
import com.itwasjoke.effectivemobile.entity.Comment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CommentMapper {
    @Mapping(source = "comment.user.email", target = "name")
    CommentResponseDTO commentToResponse(Comment comment);
}
