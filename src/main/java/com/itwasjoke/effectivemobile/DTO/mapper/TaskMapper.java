package com.itwasjoke.effectivemobile.DTO.mapper;

import com.itwasjoke.effectivemobile.DTO.TaskRequestDTO;
import com.itwasjoke.effectivemobile.DTO.TaskResponseDTO;
import com.itwasjoke.effectivemobile.entity.Task;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = CommentListMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface TaskMapper {
    Task requestToTask(TaskRequestDTO taskRequestDTO);

    @Mapping(source = "author.email", target = "authorEmail")
    @Mapping(source = "executor.email", target = "executorEmail")
    TaskResponseDTO taskToResponse(Task task);
}
